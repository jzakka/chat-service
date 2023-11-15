**Send Chat**
----
채팅 메시지 전송

* **Connect URL**
  `/ws-stomp`
* **Subscribe URL**
  `/sub/chats/:gatherId`
* **Publish URL**
  `/pub/chats/message`

* **Message Headers**
  **Required:**`Authorization: Bearer {bearer token}`

* **Data Params**

  ```json
  {
    "gatherId": "593c9f78-829e-11ee-801e-434099e86474",
    "memberId": "601ca176-829e-11ee-8c43-ebb7291389bb",
    "content": "message content"
  }
  ```