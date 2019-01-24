package main
//this project will introduce the popular go package
import (
	"param"
)

func main(){
	param.GetCommandLineParams()
	module := param.GetModule()
	method := param.GetMethod()
	distribute(module, method)
}