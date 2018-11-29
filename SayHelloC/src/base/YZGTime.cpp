/*
 * YZGTime.cpp
 *
 *  Created on: 2018年11月27日
 *      Author: Administrator
 */

#include "YZGTime.h"
#include <ctime>
#include <iostream>
using namespace std;

YZGTime::YZGTime() {
	// TODO Auto-generated constructor stub

}

YZGTime::~YZGTime() {
	// TODO Auto-generated destructor stub
}

char* YZGTime::getTimeNow(){
	time_t now = time(0);
	char * dt = ctime(&now);
	cout << "time is : " << dt << endl;

}
