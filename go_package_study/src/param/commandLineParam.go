package param

import (
	"fmt"
	"flag"
	"os"
)

const (
	progressName string = "goPackageStudy"
)

var (
	h bool
	method string
	q *bool
	
	moduleName string
)

func init(){
	flag.BoolVar(&h, "h", false, "help message")
	flag.StringVar(&method, "method", "default", "the method will call")
	q = flag.Bool("q", false, "none")
	
}

func GetCommandLineParams(){
	flag.Parse()
	moduleName = flag.Arg(0)
	//暂时不使用 -method传参
	method = flag.Arg(1)
	fmt.Println(progressName, "-----", moduleName)
}

func GetMethod() string{
	return method
}

func GetModule() string {
	return moduleName
}

func usage(){
	fmt.Fprintf(os.Stderr, `eeee version: eeee/0.0.1
Usage: goPackageStudy.ext [-h] [-method method] [-q]

Options:
`)
	flag.PrintDefaults()
}

