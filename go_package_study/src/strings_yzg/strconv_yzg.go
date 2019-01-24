package strings_yzg

import (
	"fmt"
	"strconv"
)

func TestStrconvMethod(method string){
	switch method {
	case "all":
		testStrconvAll()
	}
}

func testStrconvAll(){
	fmt.Println(strconv.ParseBool("true"))
	fmt.Println([]byte("abc"))
	fmt.Println(strconv.AppendBool([]byte("abc"), true))
	fmt.Println(strconv.Atoi("123"))
	fmt.Println(strconv.Quote("my name is aaa"))
	fmt.Println(strconv.QuoteToASCII("我是猪lalala"))
}

