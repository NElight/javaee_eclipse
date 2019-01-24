package main

import (
	"fmt"
	"strconv"
)


func map_test(){
	pmap := make(map[string]int)
	pmap["a"] = 1
	pmap["b"] = 2
	fmt.Println(pmap)
	
	var amap map[string]int
	amap = map[string]int{"one":1, "two":2}
	fmt.Println(amap)
	
	bmap := amap
	bmap["one"]= 10
	fmt.Println(amap)
	
	if _,ok := amap["one"];ok{
		fmt.Println(amap["one"])
	}
	
	delete(amap, "one")
	if _,ok := amap["one"];!ok{
		fmt.Println("amap key one has been deleted")
	}
	
	for key,value := range pmap{
		fmt.Println(key + ":" + strconv.Itoa(value))
	}
	
}

