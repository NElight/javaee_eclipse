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
#include <stdio.h>
#include <string>
#include <iostream>
#include <fstream>
#include <iomanip>

#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h> /* mmap() is defined in this header */
#include <fcntl.h>

#include <boost/program_options.hpp>

#include <boost/iostreams/stream.hpp>
#include <boost/iostreams/device/mapped_file.hpp>

#include <boost/foreach.hpp>
#include <boost/assign/std/vector.hpp> 

#include <Point.hpp>
#include <CanopyClustering.hpp>
#include <Log.hpp>
#include <program_options_misc.hpp>
#include <signal_handlers.hpp>

#include <omp.h>

using namespace std;
using namespace boost::program_options;
using namespace boost::assign;

ProfileMeasureType profile_measure;

int main(int argc, char* argv[])
{
    //
    //Initialization
    //
    
    //Set initial logging level
    log_level = logINFO;


    //Preapre Time Profile
    TimeProfile time_profile;
    time_profile.start_timer("Total");

    //Prepare variables for command line input
    string input_file_path;
    string input_filter_file;
    string output_clusters_file_path;
    string output_cluster_profiles_file;
    string output_cluster_prefix;
    string profile_measure_str;
    int num_threads;
    double max_canopy_dist;
    const double max_close_dist = 0.6; //The value is hardcoded and the option to change it removed from CLI to not confuse users
    double max_merge_dist;
    const double min_step_dist = 0.001; //The value is hardcoded and the option to change it removed from CLI to not confuse users 
    string verbosity_option;
    int filter_min_obs;
    double filter_max_top3_sample_contribution;
    int cag_filter_min_sample_obs;
    double cag_filter_max_top3_sample_contribution;
    double stop_after_num_seeds_processed;
    bool dont_create_progress_stat_file;
    string progress_stat_file;
    string not_processed_profiles_file;
    bool show_progress_bar;
    bool print_time_statistics;
    bool die_on_kill;
    const int max_num_canopy_walks = 6; //The value is hardcoded and the option to change it removed from CLI to not confuse users
    vector<string> valid_profile_measure_values;
    valid_profile_measure_values += "median", "mean", "75Q", "80Q", "85Q", "90Q", "95Q";

    //Define and read command line options
    options_description all_options_desc("Allowed options");
    options_description general_options_desc("General");
    options_description algorithm_param_options_desc("Algorithm Parameters");
    options_description filter_in_options_desc("Input filter parameters");
    options_description filter_out_options_desc("Output filter parameters");
    options_description early_stop_options_desc("Early stopping");
    options_description misc_options_desc("Miscellaneous");


    general_options_desc.add_options()
        ("input_file_path,i", value<string>(&input_file_path), "Path to the input file")
        ("output_clusters_file_path,o", value<string>(&output_clusters_file_path)->default_value("clusters_out"), "Path to file to which clusters will be written")
        ("output_cluster_profiles_file,c", value<string>(&output_cluster_profiles_file)->default_value("profiles_out"), "Path to file to which cluster profiles will be written")
        ("cluster_name_prefix,p", value<string>(&output_cluster_prefix)->default_value("CAG"), "Prefix prepended to output cluster names")
        ("num_threads,n", value<int>(&num_threads)->default_value(4), "Number of cpu threads to use.")
        ("verbosity,v", value<string>(&verbosity_option)->default_value("info"), "Control how much information should be printed to the screen. Available levels according to their verbosity: error, progress, warn, info, debug, debug1.");

    algorithm_param_options_desc.add_options()
        ("max_canopy_dist", value<double>(&max_canopy_dist)->default_value(0.1), "Max pearson correlation difference between a canopy center and a point included to the canopy")
        //This option is removed from CLI to avoid user confusion. The default value is hardcoded above
        //("max_close_dist", value<double>(&max_close_dist)->default_value(0.6), "Max pearson correlation difference between a canopy center and a point in which the point will be considered close to the canopy. As a heuristc, only points within this distance will be considered as potential neighbours during the canopy walk.")
        ("max_merge_dist", value<double>(&max_merge_dist)->default_value(0.1), "Max pearson correlation difference between two canopy centers in which the canopies should be merged. Please note, that the final canopy profiles are calculated after the merge step and consequently some final canopies might have profiles that are closer then max_merge_dist specifies.")
        //This option is removed from CLI to avoid user confusion. The default value is hardcoded above
        //("min_step_dist", value<double>(&min_step_dist)->default_value(0.001), "Min pearson correlation difference between canopy center and canopy centroid in which the centroid will be used as an origin for a new canpy (canopy walk). This is a stop criterion for canopy walk.")
        //This option is removed from CLI to avoid user confusion. The default value is hardcoded above
        //("max_num_canopy_walks", value<int>(&max_num_canopy_walks)->default_value(6), "Max number of times the canopy will walk. This is a stop criterion for canopy walk.")
        ("profile_measure", value<string>(&profile_measure_str)->default_value("75Q"), "Speicfies gene abundance measure should the algorithm use. Valid options are: \"median\", \"mean\", \"75Q\", \"80Q\", \"85Q\", \"90Q\", \"95Q\" where \"XXQ\" stands for XXth quantile measure.");

    filter_in_options_desc.add_options()
        ("filter_min_obs", value<int>(&filter_min_obs)->default_value(3), "Discard those profiles which have fewer than N non-zero samples. Setting it to 0 will disable the filter.")
        ("filter_max_top3_sample_contribution", value<double>(&filter_max_top3_sample_contribution)->default_value(0.9), "Discard those profiles for which top 3 samples constitute more than X fraction of the total signal. Setting it to 1 will disable the filter")
        ("input_filter_file", value<string>(&input_filter_file)->default_value(""), "The file to which profiles filtered out by either of the input filters will be written");

    filter_out_options_desc.add_options()
        ("cag_filter_min_sample_obs", value<int>(&cag_filter_min_sample_obs)->default_value(3), "Return only those canopies that have at least N non-zero cluster profile observations. Setting it to 0 will disable the filter.")
        ("cag_filter_max_top3_sample_contribution", value<double>(&cag_filter_max_top3_sample_contribution)->default_value(0.9), "Don't return canopies where top three(or less) samples constitute more than X fraction of the total profile signal. Setting it to 1 disables the filter.");

    early_stop_options_desc.add_options()
        ("stop_criteria", value<double>(&stop_after_num_seeds_processed)->default_value(50000), "Stop clustering after X number of seeds have been processed. Setting it to 0 will disable this stop criterion.");

    misc_options_desc.add_options()
        ("die_on_kill", bool_switch(&die_on_kill), "If set, after receiving a KILL signal, the program will die and no results will be produced. By default clustering will stop but clusters will be merged and partial results will be printed as usual.")
        ("not_processed_profiles_file", value<string>(&not_processed_profiles_file)->default_value(""), "Path to file to which unprocessed profiles will be dumped at KILL signal")
        ("print_time_statistics,t", bool_switch(&print_time_statistics), "Print wall clock time profiles of various analysis parts. This is not aggressive and won't increase compuatation time.")
        ("show_progress_bar,b", bool_switch(&show_progress_bar), "Show progress bar, nice if output is printed to console, don't use if you are redirecting to a file. Verbosity must be set to at least PROGRESS for it to have an effect.") 
        ("dont_create_progress_stat_file", bool_switch(&dont_create_progress_stat_file), "If set, the canopy progress file will not be created.")
        ("progress_stat_file", value<string>(&progress_stat_file)->default_value("canopy_progress.out"), "Name of the canopy size statistics file. To this file current progress after each processed seed profile will be dumped in format <index> <num_profiles_left> <this_canopy_size> <total_num_thread_collisions>")
        ("help", "write help message");

    all_options_desc.add(general_options_desc).add(algorithm_param_options_desc).add(filter_in_options_desc).add(filter_out_options_desc).add(early_stop_options_desc).add(misc_options_desc);

    positional_options_description command_line_positional_desc;
    command_line_positional_desc.add("input_file_path",1);
    command_line_positional_desc.add("output_clusters_file_path",1);
    command_line_positional_desc.add("output_cluster_profiles_file",1);

    variables_map command_line_variable_map;
    store(command_line_parser(argc,argv).options(all_options_desc).positional(command_line_positional_desc).run(), command_line_variable_map);
    notify(command_line_variable_map);

    //
    //Verify command line input parameters
    //
    //verify_input_correctness(all_options_desc, command_line_variable_map);
    if (command_line_variable_map.count("help") || argc < 3) {
        cout << "Usage: cc.bin [options] PROFILES_INPUT_FILE CLUSTERS_OUTPUT_FILE" << endl << endl;;
        cout << all_options_desc<< "\n";
        exit(1);
    }

    check_if_file_is_readable("input_file_path",input_file_path);
    check_if_file_is_writable("output_clusters_file_path",output_clusters_file_path);
    check_if_file_is_writable("output_cluster_profiles_file",output_cluster_profiles_file);
    check_if_file_is_writable("input_filter_file",input_filter_file);
    vector<string> valid_verbosities;
    valid_verbosities += "error", "progress", "warn", "info", "debug", "debug1", "debug2", "debug3";
    check_if_one_of("verbosity_option",verbosity_option, valid_verbosities);
    check_if_within_bounds("num_threads",num_threads,1,999);//Not exactly future proof, but let's put foolproofness first
    check_if_within_bounds("max_canopy_dist",max_canopy_dist,0.0,1.0);
    check_if_within_bounds("max_close_dist",max_close_dist,0.0,1.0);
    check_if_within_bounds("max_merge_dist",max_merge_dist,0.0,1.0);
    check_if_within_bounds("min_step_dist",min_step_dist,0.0,1.0);
    check_if_within_bounds("max_num_canopy_walks",max_num_canopy_walks,0,100);
    check_if_one_of("profile_measure", profile_measure_str, valid_profile_measure_values);

    check_if_within_bounds("filter_min_obs",filter_min_obs,0,10000);
    check_if_within_bounds("filter_max_top3_sample_contribution",filter_max_top3_sample_contribution,0.0,1.0);
    check_if_within_bounds("cag_filter_min_sample_obs",cag_filter_min_sample_obs,0,10000);
    check_if_within_bounds("cag_filter_max_top3_sample_contribution",cag_filter_max_top3_sample_contribution,0.0,1.0);
    bool create_progress_stat_file = ! dont_create_progress_stat_file;
    if(create_progress_stat_file)
        check_if_file_is_writable("progress_stat_file",progress_stat_file);
    if(not_processed_profiles_file!= "")
        check_if_file_is_writable("not_processed_profiles_file",not_processed_profiles_file);

    //
    //Set appropriate profile measure method to the global var (ugh..)
    //
    if(profile_measure_str == "median"){
        profile_measure = MEDIAN;
    } else if(profile_measure_str == "mean"){
        profile_measure = MEAN;
    } else if(profile_measure_str == "75Q"){
        profile_measure = PERCENTILE_75;
    } else if(profile_measure_str == "80Q"){
        profile_measure = PERCENTILE_80;
    } else if(profile_measure_str == "85Q"){
        profile_measure = PERCENTILE_85;
    } else if(profile_measure_str == "90Q"){
        profile_measure = PERCENTILE_90;
    } else if(profile_measure_str == "95Q"){
        profile_measure = PERCENTILE_95;
    } else {
        cout << "Unknown type of profile measure method: \"" << profile_measure_str << "\"" << endl;
        cout << "This is most likely a programming error, please report this bug" << endl;
        exit(1);
    }

    //
    //Set user chosen logging level
    //
    if(verbosity_option == "error"){
        log_level = logERR;
    }else if(verbosity_option == "progress"){
        log_level = logPROGRESS;
    }else if(verbosity_option == "warn"){
        log_level = logWARN;
    }else if(verbosity_option == "info"){
        log_level = logINFO;
    }else if(verbosity_option == "debug"){
        log_level = logDEBUG;
    }else if(verbosity_option == "debug1"){
        log_level = logDEBUG1;
    }else if(verbosity_option == "debug2"){
        log_level = logDEBUG2;
    }else if(verbosity_option == "debug3"){
        log_level = logDEBUG3;
    }

    _log(logINFO) << "";
    _log(logINFO) << "Files:";
    _log(logINFO) << "input_file_path:\t " << input_file_path;
    _log(logINFO) << "output_cluster_profiles_file:\t " << output_cluster_profiles_file;
    _log(logINFO) << "progress_stat_file:\t " << progress_stat_file;
    _log(logINFO) << "not_processed_profiles_file:\t " << not_processed_profiles_file;
    _log(logINFO) << "input_filter_file:\t " << input_filter_file;
    _log(logINFO) << "";
    

    //Set signal handler
    if(die_on_kill) 
        signal(SIGINT, signal_callback_die_handler);
    else    
        signal(SIGINT, signal_callback_gentle_handler);

    //Set number of threads
    _log(logINFO) << "";
    _log(logINFO) << "General:";
    _log(logINFO) << "num_threads:\t " << num_threads;
    _log(logINFO) << "";

    omp_set_num_threads(num_threads);

    //
    //Parse point description file
    //
    time_profile.start_timer("File loading");

    vector<Point*> points;
    vector<Point*> filtered_points;

    int point_file;
    char* point_file_mmap;
    struct stat statbuf;

    

    /* open the input file */
    point_file = open(input_file_path.c_str(), O_RDONLY);

    /* find size of input file */
    fstat(point_file,&statbuf);

    /* mmap the input file */
    point_file_mmap = (char*)mmap (0, statbuf.st_size, PROT_WRITE, MAP_PRIVATE, point_file, 0);

    time_profile.stop_timer("File loading");
    time_profile.start_timer("Reading profiles");

    _log(logINFO) << "File loaded into memory, generating profiles";

    char* line_start_ptr = point_file_mmap;
    char* line_end_ptr = point_file_mmap;
    char* mmap_end_ptr = point_file_mmap + statbuf.st_size;
    while(line_start_ptr < mmap_end_ptr){
        line_end_ptr = line_start_ptr;
        while(*line_end_ptr != '\n' && *line_end_ptr != '\r' && line_end_ptr < mmap_end_ptr){
            line_end_ptr++;
        }
        if(line_end_ptr != line_start_ptr){//Check if the line is not empty
            *line_end_ptr = '\0';
            //cout << line_start_ptr << endl;
            points.push_back(new Point(line_start_ptr));
        }
        line_start_ptr = ++line_end_ptr;
        die_if_true(terminate_called);
    }

    time_profile.stop_timer("Reading profiles");

    _log(logINFO) << "Profiles read, dropping file from memory";
    _log(logINFO) << "";

    /* drop the file from memory*/
    munmap(point_file_mmap, statbuf.st_size);
    
    _log(logINFO) << "Running basic validation of profiles";
    _log(logINFO) << "filter_max_top3_sample_contribution:\t " << filter_max_top3_sample_contribution;
    _log(logINFO) << "filter_min_obs:\t " << filter_min_obs;
    _log(logINFO) << "";

    time_profile.start_timer("Profiles validation");
    verify_proper_point_input_or_die(points);
    time_profile.stop_timer("Profiles validation");

    vector<Point*> points_filtered_out_due_to_three_point_proportion_filter;
    vector<Point*> points_filtered_out_due_to_num_non_zero_samples_filter;

    time_profile.start_timer("Input profiles filtering");

#pragma omp parallel for shared(points_filtered_out_due_to_three_point_proportion_filter, points_filtered_out_due_to_num_non_zero_samples_filter, filtered_points)
    for(int i = 0; i < points.size(); i++){
        //Both filters are set
        if((filter_min_obs > 0) && (filter_max_top3_sample_contribution< 0.9999)){
            bool point_is_valid = true;
                
            if( ! points[i]->check_if_num_non_zero_samples_is_greater_than_x(filter_min_obs))
            {
#pragma omp critical
                points_filtered_out_due_to_num_non_zero_samples_filter.push_back(points[i]);
                point_is_valid = false;
            }

            if( ! points[i]->check_if_top_three_point_proportion_is_smaller_than(filter_max_top3_sample_contribution))
            {
#pragma omp critical
                points_filtered_out_due_to_three_point_proportion_filter.push_back(points[i]);
                point_is_valid = false;
            }

            if(point_is_valid){
#pragma omp critical
                filtered_points.push_back(points[i]);
            }
        } else if (filter_min_obs > 0){ 
            if(points[i]->check_if_num_non_zero_samples_is_greater_than_x(filter_min_obs)){
#pragma omp critical
                filtered_points.push_back(points[i]);
            }
            else 
            {
#pragma omp critical
                points_filtered_out_due_to_num_non_zero_samples_filter.push_back(points[i]);
            }
        } else if (filter_max_top3_sample_contribution< 0.9999){ 
            if(points[i]->check_if_top_three_point_proportion_is_smaller_than(filter_max_top3_sample_contribution)){ 
#pragma omp critical
                filtered_points.push_back(points[i]);
            }
            else 
            {
#pragma omp critical
                points_filtered_out_due_to_three_point_proportion_filter.push_back(points[i]);
            }
        }
    }
    if(input_filter_file != ""){
        ofstream filtered_point_file;
        filtered_point_file.open(input_filter_file.c_str(), ios::out | ios::trunc);
        filtered_point_file << "#filtered_profile_id\tinput_filter_name\n";
        for(int i = 0; i < points_filtered_out_due_to_num_non_zero_samples_filter.size(); i++){
            filtered_point_file << points[i]->id << "\t" << "min_observations_filter" << "\n";
        }
        for(int i = 0; i < points_filtered_out_due_to_three_point_proportion_filter.size(); i++){
            filtered_point_file << points[i]->id << "\t" << "max_top3_sample_contribution_filter" << "\n";
        }
        filtered_point_file.close();
    }

    time_profile.stop_timer("Input profiles filtering");
    _log(logINFO) << "Number of profiles filtered out due to three sample signal contribution filter: " << points_filtered_out_due_to_three_point_proportion_filter.size();
    _log(logINFO) << "Number of profiles filtered out due to non zero samples number filter: " << points_filtered_out_due_to_num_non_zero_samples_filter.size();
    _log(logINFO) << "Number of profiles filtered out: " << points.size() - filtered_points.size(); 

    _log(logINFO) << "Finished input profiles processing";
    
    _log(logINFO) << "Number of profiles after filtering: " << filtered_points.size();
    
    die_if_true(terminate_called);
    die_if_true(filtered_points.size() < 1);
    //
    //Run Canopy Clustering
    //
    std::vector<Canopy*> canopies;

    canopies = CanopyClusteringAlg::multi_core_run_clustering_on(filtered_points, num_threads, max_canopy_dist, max_close_dist, max_merge_dist, min_step_dist, max_num_canopy_walks, stop_after_num_seeds_processed, create_progress_stat_file, progress_stat_file, not_processed_profiles_file, show_progress_bar, time_profile);

    _log(logINFO) << "Finished clustering";

    //
    //Filter out canopies
    //

    if(cag_filter_min_sample_obs){
        time_profile.start_timer("Filtering canopies by minimum number of sample detections");
        CanopyClusteringAlg::filter_clusters_by_zero_medians(cag_filter_min_sample_obs, canopies);
        _log(logINFO) << "Finished filtering for minimum number of sample detections, number of canopies:" << canopies.size();
        time_profile.stop_timer("Filtering canopies by minimum number of sample detections");
    }


    if(cag_filter_max_top3_sample_contribution< 0.99999){ //It's due to a double comparison
        time_profile.start_timer("Filtering canopies by three sample signal contribution proportion");
        CanopyClusteringAlg::cag_filter_max_top3_sample_contribution(cag_filter_max_top3_sample_contribution, canopies);
        _log(logINFO) << "Finished filtering by three sample signal contribution proportion, number of canopies:" << canopies.size();
        time_profile.stop_timer("Filtering canopies by three sample signal contribution proportion");
    }

    {
        time_profile.start_timer("Filtering canopies by size");
        CanopyClusteringAlg::filter_clusters_by_size(canopies);
        _log(logINFO) << "Finished filtering by size(number of neighbours must be bigger than 1), number of canopies:" << canopies.size();
        time_profile.stop_timer("Filtering canopies by size");
    }

    _log(logPROGRESS) << "";
    _log(logPROGRESS) << "####################Writing Results####################" ;
    ofstream out_file;

    sort(canopies.begin(), canopies.end(), compare_canopy_ptrs_by_canopy_size);

    int num_digits = ceil(log10(canopies.size()));
    cout << std::setfill('0');


    int i =1;
    out_file.open(output_clusters_file_path.c_str(), ios::out | ios::trunc);
    BOOST_FOREACH(Canopy* c, canopies){
        BOOST_FOREACH(Point* p, c->neighbours){
            out_file << output_cluster_prefix << std::setw(num_digits) << std::setfill('0') << i << "\t";
            out_file << p->id << "\n";
        }
        i++;
    }
    out_file.close();

    i=1;
    out_file.open(output_cluster_profiles_file.c_str(), ios::out | ios::trunc);
    BOOST_FOREACH(Canopy* c, canopies){
        out_file << output_cluster_prefix << std::setw(num_digits) << std::setfill('0') << i << "\t";
        
        for(int j=0; j < c->center->num_data_samples; j++){
            out_file << c->center->sample_data[j] << "\t" ;
        }

        i++;
        out_file << "\n";
    }
    out_file.close();

    //
    //Clean up
    //
    BOOST_FOREACH(Canopy* c, canopies)
        delete c;

    BOOST_FOREACH(Point* point, points)
        if(point)//Some points were centers of canopies and were deleted already
            delete point;


    time_profile.stop_timer("Total");
    //Write output statistics
    if(print_time_statistics){
        cout << time_profile << endl;
    }


    return 0;
}
