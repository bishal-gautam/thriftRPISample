service ByteTestWithThrift {
	i32 testByte(1:binary data),
	string testMessage(1:string name),
}
