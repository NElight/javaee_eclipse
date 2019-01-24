package strings_yzg

import (
	"fmt"
	"strings"
	"unicode"
)

func TestStringMethod(stringMethod string){
	switch stringMethod {
	case "contains":
		testStringContains()
	case "containsAny":
		testStringContainsAny()
	case "containsRune":
		testStringContainsRune()	
	case "subStringCount":
		testStringCount()
	case "seprate":
		testStringField()	
	case "all":
		testStringFunc()
	}
}

func testStringContains(){
	fmt.Println(strings.Contains("hello world", "world"))
	fmt.Println(strings.Contains("ljfddlasfj", "dfasfa"))
}

func testStringContainsAny(){
	fmt.Println(strings.ContainsAny("i am a teacher", "i"))
	fmt.Println(strings.ContainsAny("abcdefg", "wwwa"))
}

func testStringContainsRune(){
	fmt.Println(rune('m'))
	fmt.Println(rune('谢'))
	fmt.Println(strings.ContainsRune("谢谢你", rune('谢')))
}

func testStringCount(){
	fmt.Println(strings.Count("abccceeedddffsaaaeee", "eee"))
}

func testStringField(){
	fmt.Println(strings.Fields("i am a teacher"))
}



func testStringFunc(){
	fmt.Println(strings.Repeat("abc", 3))
	fmt.Println(strings.Replace("hello world world", "world", "light", -1))
	fmt.Println(strings.Trim("!! !!! hello ! world ! !!", "! "))
	fmt.Println(strings.Fields("a b c d e"))
	
	fSep := func (c rune) bool {
		return !unicode.IsLetter(c) && !unicode.IsDigit(c)
	}
	
	fmt.Println(strings.FieldsFunc("foo1;foo2 foo3!", fSep))
	fmt.Println(strings.Split("my name is a  safjlajf  a a a d  df d faf a ", "a "))
	
}
