package main

import (
	"log"

	"golang.org/x/mobile/app"
	"golang.org/x/mobile/event/key"
	"golang.org/x/mobile/event/lifecycle"
	"golang.org/x/mobile/event/mouse"
	"golang.org/x/mobile/event/paint"
	"golang.org/x/mobile/event/touch"
)

func main() {
	log.Println("main.run")
	app.Main(func(a app.App) {
		log.Println("app.Main")
		for ev := range a.Events() {
			switch e := a.Filter(ev).(type) {
			case lifecycle.Event:
				log.Println(e)
				if e.To == lifecycle.StageDead {
					return
				}
			case touch.Event:
				log.Println(e)
			case key.Event:
				log.Println(e)
			case mouse.Event:
				log.Println(e)
			case paint.Event:
				log.Println(e)
			}
		}
	})
}
