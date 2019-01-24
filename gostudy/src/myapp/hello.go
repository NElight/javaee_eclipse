package main 

import (
	"fmt"
)

const Pi = 3.14151692653
const (
	a = iota
	b = iota
	c = iota
)

var (
	// static 
	// b string
)

const (
	const1 string = "abc"
	const2 int = 2
)


func main() {
	
	//var a = "cronaab"
	//var b string = "alncn"
	c1 := "ajflajf"
	
	fmt.Println("hello go")
	fmt.Println(c1)
	
	getOS()
	getTime()
	getLocation()
	slice_test()
	map_test()
	getMem()
	inter_test()
	//test_input()
	//bufio_test()
	//chan_test()
	//startNetServer();
}

