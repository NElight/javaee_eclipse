package main

import (
	"fmt"
)

func getLocation(){
	var i int = 1
	fmt.Printf("i = %d, Location in memory :%p\n", i, &i)
}

