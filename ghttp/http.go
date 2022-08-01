package ghttp

import (
	"io/ioutil"
	"net/http"
)

func Get(url string) (string, error) {
	resp, e := http.Get(url)
	if e != nil {
		return "", e
	}
	defer resp.Body.Close()
	bs, e := ioutil.ReadAll(resp.Body)
	if e != nil {
		return "", e
	}
	return string(bs), nil
}
