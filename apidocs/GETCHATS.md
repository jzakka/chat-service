**Get Chat**
----
채팅 메시지 불러오기. <br/>
seq번호를 지정하면 해당 seq번호부터 페이지네이션해서 불러옴 <br/>
seq번호 미지정시 처음부터 불러옴

* **URL** <br/>
  `/chats/:gatherId`

* **Method** <br/>
  `GET`

* **Request Headers**<br/>
  **Required:** `Authorization=Bearer {token}`

* **Query Params**<br/>
  **Optional:**<br/>
  `seq=[Integer]`

* **Success Response**
  * **Code:** 200<br/>
    **Content:**<br/>
    ```json
    [
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id99",
        "content": "테스트 메시지99",
        "createAt": "2023-11-15T22:27:08.742",
        "sequence": 100
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id98",
        "content": "테스트 메시지98",
        "createAt": "2023-11-15T22:27:08.741",
        "sequence": 99
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id97",
        "content": "테스트 메시지97",
        "createAt": "2023-11-15T22:27:08.74",
        "sequence": 98
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id96",
        "content": "테스트 메시지96",
        "createAt": "2023-11-15T22:27:08.738",
        "sequence": 97
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id95",
        "content": "테스트 메시지95",
        "createAt": "2023-11-15T22:27:08.737",
        "sequence": 96
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id94",
        "content": "테스트 메시지94",
        "createAt": "2023-11-15T22:27:08.735",
        "sequence": 95
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id93",
        "content": "테스트 메시지93",
        "createAt": "2023-11-15T22:27:08.734",
        "sequence": 94
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id92",
        "content": "테스트 메시지92",
        "createAt": "2023-11-15T22:27:08.733",
        "sequence": 93
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id91",
        "content": "테스트 메시지91",
        "createAt": "2023-11-15T22:27:08.73",
        "sequence": 92
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id90",
        "content": "테스트 메시지90",
        "createAt": "2023-11-15T22:27:08.729",
        "sequence": 91
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id89",
        "content": "테스트 메시지89",
        "createAt": "2023-11-15T22:27:08.727",
        "sequence": 90
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id88",
        "content": "테스트 메시지88",
        "createAt": "2023-11-15T22:27:08.726",
        "sequence": 89
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id87",
        "content": "테스트 메시지87",
        "createAt": "2023-11-15T22:27:08.725",
        "sequence": 88
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id86",
        "content": "테스트 메시지86",
        "createAt": "2023-11-15T22:27:08.723",
        "sequence": 87
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id85",
        "content": "테스트 메시지85",
        "createAt": "2023-11-15T22:27:08.721",
        "sequence": 86
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id84",
        "content": "테스트 메시지84",
        "createAt": "2023-11-15T22:27:08.72",
        "sequence": 85
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id83",
        "content": "테스트 메시지83",
        "createAt": "2023-11-15T22:27:08.719",
        "sequence": 84
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id82",
        "content": "테스트 메시지82",
        "createAt": "2023-11-15T22:27:08.717",
        "sequence": 83
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id81",
        "content": "테스트 메시지81",
        "createAt": "2023-11-15T22:27:08.716",
        "sequence": 82
      },
      {
        "gatherId": "test-gather-id",
        "memberId": "test-member-id80",
        "content": "테스트 메시지80",
        "createAt": "2023-11-15T22:27:08.715",
        "sequence": 81
      }
    ]
    ```
