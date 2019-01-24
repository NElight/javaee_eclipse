package main

import (
	"fmt"
	"runtime"
	"os"
)

func getOS(){
	var goos string = runtime.GOOS
	fmt.Printf("the os is %s\n", goos)
	path := os.Getenv("PATH")
	fmt.Printf("Path is %s\n", path)
}

