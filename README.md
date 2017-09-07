# MVC 모델을 활용한 MEMO 만들기
Memo를 Model, View, Controller 로 구분하여 개발

## __문제__
명령어를 입력받아 아래와 같은 Memo와 관련된 업무를 후속처리하는 프로그램 개발
1. Memo 생성
2. Memo 읽기
3. Memo 수정
4. Memo 삭제
5. Memo 리스트 조회

### __설명__
#### ● ex> Memo 생성
1. View에서 사용자를 통해 Memo를 입력받고 Controller에게 넘김
2. Controller는 Model에게 저장 명령
3. Model은 입력받은 Memo를 저장</br>
![](https://github.com/Lee-KyungSeok/MemoExample/blob/master/picture/mvcCreate.png)

#### ● 저장되는 Memo 객체
 1. Memo 번호
 2. 작성자
 3. 내용
 4. Memo가 작성된 시간
``` java
// 개별 글 한개를 저장하는 클래스, 게시판 글 하나가 Memo class 한개
class Memo{
	int no;
	String name;
	String content;
	long datetime;
}
```

#### ● Model 설명

__소스 코드__
``` java

```

#### ● View 설명

#### ● Controller 설명

__소스 코드__
``` java

```
