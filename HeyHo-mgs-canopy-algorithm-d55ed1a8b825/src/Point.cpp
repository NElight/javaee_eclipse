/**
 * Metagenomics Canopy Clustering Implementation
 *
 * Copyright (C) 2013, 2014 Piotr Dworzynski (piotr@cbs.dtu.dk), Technical University of Denmark
 *
 * This file is part of Metagenomics Canopy Clustering Implementation.
 *
 * Metagenomics Canopy Clustering Implementation is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Metagenomics Canopy Clustering Implementation is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */
#include <iostream>
#include <sstream>
#include <algorithm>
#include <assert.h>
#include <stdio.h>
#include <string.h>
#include <functional>
#include <numeric>
#include <limits>

#include <boost/bind.hpp>
#include <boost/foreach.hpp>
#include <boost/functional/hash.hpp>
#include <boost/algorithm/string.hpp>

#include <Point.hpp>
#include <Log.hpp>
#include <Stats.hpp>

using namespace std;

extern ProfileMeasureType profile_measure;

Point::Point(const char* line){
    //Copy line to private buffer - strtok will modify it
    char* private_line = new char[strlen(line) + 1];
    strcpy(private_line,line);
    _log(logDEBUG3)<< "Point constructor, got: \"" << line << "\""; 

    //Read gene id - first word in the line
    char* word = strtok(private_line, "\t ");
    id = string(word);
    _log(logDEBUG3)<< "Point constructor, point id: \"" << id << "\""; 

    //Fill vector with data samples
    std::vector<double> sample_data_vector;
    sample_data_vector.reserve(700);

    word = strtok(NULL, "\t ");
    while( word != NULL ){
        sample_data_vector.push_back((double)atof(word));
        word = strtok(NULL, "\t ");
    }

    //Get number of samples for this point
    num_data_samples = sample_data_vector.size();
    _log(logDEBUG3)<< "Point constructor, num data samples: \"" << num_data_samples << "\""; 

    //Allocate and copy samples into array
    sample_data = new double[num_data_samples];
    sample_data_pearson_precomputed = new double[num_data_samples]; 
    for(int i = 0; i < sample_data_vector.size(); i++){
        sample_data[i] = sample_data_vector[i];
    }

    precompute_pearson_data(num_data_samples, sample_data, sample_data_pearson_precomputed);

    delete private_line;
}

Point::Point(const Point& p){
    id = p.id;
    num_data_samples = p.num_data_samples;

    sample_data = new double[num_data_samples];
    for(int i=0; i < num_data_samples;i++){
        sample_data[i] = p.sample_data[i];
    }

    sample_data_pearson_precomputed = new double[num_data_samples];
    for(int i=0; i < num_data_samples;i++){
        sample_data_pearson_precomputed[i] = p.sample_data_pearson_precomputed[i];
    }
}


Point::~Point(){
    delete sample_data;
    delete sample_data_pearson_precomputed;
}


bool Point::check_if_num_non_zero_samples_is_greater_than_x(int x){
    int num_non_zero_samples = 0;
    for(int i=0; i < num_data_samples; i++){
        if(sample_data[i] > std::numeric_limits<double>::min()){
            num_non_zero_samples++;
            if(num_non_zero_samples >= x)
                return true;
        }
    }
    return false;

}

bool Point::check_if_top_three_point_proportion_is_smaller_than(double x){

    vector<double> temp_data_samples;
    temp_data_samples.resize(num_data_samples, 0.0);
    std::copy(sample_data, sample_data + num_data_samples, temp_data_samples.begin());

    std::sort(temp_data_samples.begin(), temp_data_samples.end());
    std::reverse(temp_data_samples.begin(), temp_data_samples.end());

    double sum_data_samples = std::accumulate(temp_data_samples.begin(), temp_data_samples.end(), 0.0 );
    double sum_top_three = temp_data_samples[0] + temp_data_samples[1] + temp_data_samples[2]; 

    if(sum_data_samples > std::numeric_limits<double>::min()){
        return (sum_top_three / sum_data_samples) < x - std::numeric_limits<double>::min();
    } else {
        //All samples have 0 value - can't divide by 0
        return false;
    }

}

void verify_proper_point_input_or_die(const std::vector<Point*>& points){
    
    //Verify all points have the same number of samples
    int num_samples = points[0]->num_data_samples;
    BOOST_FOREACH(const Point* point, points){
        assert(point->num_data_samples == num_samples);
    }

    _log(logINFO) << "Finished reading profiles input file";
    _log(logINFO) << "Observed number of samples per profile: " << num_samples;
    _log(logINFO) << "Number of profiles read: " << points.size();

}

double get_distance_between_points(const Point* p1, const Point* p2){

    int len = p1->num_data_samples;
    double dist = 1 - pearsoncorr_from_precomputed(len, p1->sample_data_pearson_precomputed, p2->sample_data_pearson_precomputed);

    //if(log_level >= logDEBUG3){
    //    _log(logDEBUG3) << "<<<<<<DISTANCE<<<<<<";
    //    _log(logDEBUG3) << "point: " << p1->id;
    //    for(int i=0; i < p1->num_data_samples; i++){
    //        _log(logDEBUG3) << "\t"<<p1->sample_data[i];
    //    }
    //    _log(logDEBUG3) << "point: " << p2->id;
    //    for(int i=0; i < p2->num_data_samples; i++){
    //        _log(logDEBUG3) << "\t"<<p2->sample_data[i];
    //    }
    //    _log(logDEBUG3) << "distance: " << dist;
    //}

    return dist; 
}

Point* get_centroid_of_points(const std::vector<Point*>& points){

    assert(points.size());

    Point* centroid = new Point(*(points[0]));
    centroid->id = "!GENERATED!";

    const int num_samples = points[0]->num_data_samples;
    const int num_points = points.size();

    //_log(logDEBUG4) << "num samples: " << num_samples;

    for(int i = 0; i < num_samples; i++){

        std::vector<double> point_samples;

        BOOST_FOREACH(const Point* p, points){

            //TODO: this is slow 
            point_samples.push_back(p->sample_data[i]);

        }
        if(profile_measure == MEAN){
            double sum = std::accumulate(point_samples.begin(), point_samples.end(), 0);
            double mean = sum / point_samples.size();

            centroid->sample_data[i] = mean;
        } else {
            std::sort(point_samples.begin(), point_samples.end());


            //Number which multiplied with length of the vector
            //will give us the element corresponding to the percentile
            double percentile_multiplier = -1; 

            switch (profile_measure) {
                case MEDIAN:
                    percentile_multiplier = 0.5;
                    break;
                case PERCENTILE_75:
                    percentile_multiplier = 0.75;
                    break;
                case PERCENTILE_80:
                    percentile_multiplier = 0.80;
                    break;
                case PERCENTILE_85:
                    percentile_multiplier = 0.85;
                    break;
                case PERCENTILE_90:
                    percentile_multiplier = 0.90;
                    break;
                case PERCENTILE_95:
                    percentile_multiplier = 0.95;
                    break;
            }

            assert(percentile_multiplier != -1);

            //make correction for counting from 0
            //so median in vector of length 5 would be (5 - 1)*0.5 = 2
            double target_element_i = (num_points - 1)*percentile_multiplier; //We cannot use it since it might (and usually will be) a float like 2.25
            int lower_element_i = floor(target_element_i);
            int upper_element_i = ceil(target_element_i);

            //now we want to take value which is proportional to the percentile
            double lower_to_upper_proportion = target_element_i - lower_element_i;
            double lower_element_val = point_samples[lower_element_i];
            double upper_element_val = point_samples[upper_element_i];

            double percentile = lower_element_val + lower_to_upper_proportion * (upper_element_val - lower_element_val);

            centroid->sample_data[i] = percentile;
        }
    }

    precompute_pearson_data(centroid->num_data_samples, centroid->sample_data, centroid->sample_data_pearson_precomputed);
    
    return centroid;
}


std::size_t hash_value(const Point& p){
    boost::hash<std::string> hasher;
    return hasher(p.id);
}

std::ostream& operator<<(std::ostream& ost, const Point& p)
{
        ost << "============================" << std::endl;
        ost << "Point: " << p.id << std::endl;
        for(int i=0; i < p.num_data_samples; i++){
            ost << p.sample_data[i] << "\t" ;
        }
        ost << std::endl;
        ost << "============================" << std::endl;
        
        return ost;
}

