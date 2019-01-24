package main

import (
	"fmt"
)

func slice_test(){
	var a = []string {"a", "b", "c", "d"}
	fmt.Println(a)
	a = a[0:3]
	fmt.Println(a)
	var b = make([]string, 10)
	copy(b, a)
	fmt.Println(b)
	b = append(b, "e", "f")
	fmt.Println(b)
	fmt.Println(len(b))
	fmt.Println(cap(b))
	c := new([10]string)[0:5]
	copy(c, b)
	fmt.Println(c)
}

