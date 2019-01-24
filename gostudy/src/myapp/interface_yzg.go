package main

import (
	"fmt"
	"math"
)

type Square struct {
	side float32
}

type Circle struct {
	radius float32
}

type Shape interface {
	Area() float32
}

func (sq *Square) Area() float32{
	return sq.side * sq.side
}

func (cr *Circle) Area() float32{
	return cr.radius * cr.radius * math.Pi
}

func inter_test(){
	var sh Shape
	sq1 := new(Square)
	sq1.side = 5
	if t,ok := sh.(*Square); ok {
		fmt.Printf("the type of sh is %T\n", t)
	}
	if t,ok:= sh.(*Circle); ok {
		fmt.Printf("the type of sh is %T\n", t);
	}else {
		fmt.Println("not square nor circle")
	}
}

