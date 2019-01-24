package main

import (
	"fmt"
	"runtime"
)

func getMem(){
	var m runtime.MemStats
	runtime.ReadMemStats(&m)
	fmt.Printf("%d kb\n", m.Alloc / 1024)
}

