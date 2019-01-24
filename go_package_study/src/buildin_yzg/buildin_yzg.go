package buildin_yzg

import (
	"fmt"
)

func TestBuildinFunc(method string){
	switch method{
		case "panic":
			testPanic()
	}
}

func testPanic(){
	defer func(){
		if r:= recover();r!=nil{
			fmt.Printf("recover in f:%v\n", r)
		}
	}()
	fmt.Println("calling g")
	g(0)
	fmt.Println("returned normally from testPanic")
	
	
}

func g(i int){
	if i > 3{
		fmt.Println("panicing!")
		panic(fmt.Sprintf("%v", i))
	}
	defer fmt.Println("defer in g", i)
	fmt.Println("print in g ", i)
	g(i+1)
}
