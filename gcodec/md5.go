package gcodec

import (
	"crypto/md5"
	"encoding/hex"
	"io"
	"os"
)

func MD5String(text string) string {
	bs := md5.Sum([]byte(text))
	return hex.EncodeToString(bs[:])
}

func MD5Bytes(bs []byte) string {
	rtn := md5.Sum(bs)
	return hex.EncodeToString(rtn[:])
}

func MD5File(file string) (string, error) {
	f, e := os.OpenFile(file, os.O_RDONLY, 0777)
	if e != nil {
		return "", e
	}
	defer f.Close()
	var h = md5.New()
	_, e = io.Copy(h, f)
	if e != nil {
		return "", e
	}
	bs := h.Sum(nil)
	return hex.EncodeToString(bs), nil
}
