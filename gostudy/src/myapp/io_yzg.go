package main

import (
	"fmt"
	"bufio"
	"os"
)

var (
	firstName, lastName, s string
	i int
	f float32
	input = "56.12 / 5212 / Go"
	format = "%f / %d / %s"
)

func test_input(){
//	fmt.Println("please input your full name")
//	fmt.Scanln(&firstName, &lastName)
//	fmt.Printf("hi %s %s!\n", firstName, lastName)
	
	fmt.Sscanf(input, format, &f, &i, &s)
	fmt.Println("from the string wo read is ", f, i, s)
}

var inputReader *bufio.Reader
var input1 string
var err error

func bufio_test(){
	inputReader = bufio.NewReader(os.Stdin)
	fmt.Println("please input some thing")
	input1,err = inputReader.ReadString('\n')
	if err == nil {
		fmt.Println("the  input str is ", input1)
	}
}

