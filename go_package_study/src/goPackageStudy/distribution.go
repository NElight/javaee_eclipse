package main

import (
	"fmt"
	"strings_yzg"
	"buildin_yzg"
)

func distribute(module string, method string) {
	fmt.Println("module :" + module + "------" + "method : ", method, "-----", "will distribute")
	fmt.Println("---------------------")
	
	switch module {
	case "strings":
		strings_yzg.TestStringMethod(method)
	case "strconv":
		strings_yzg.TestStrconvMethod(method)
	case "buildin":
		buildin_yzg.TestBuildinFunc(method)
	}
	
	fmt.Println("---------------------")
}

