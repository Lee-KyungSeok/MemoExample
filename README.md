# MVC 모델을 활용한 MEMO 만들기
Memo를 Model, View, Controller 로 구분하여 개발

## __문제__
명령어를 입력받아 아래와 같은 Memo와 관련된 업무를 후속처리하는 프로그램 개발
1. Memo 생성
2. Memo 읽기
3. Memo 수정
4. Memo 삭제
5. Memo 리스트 조회

## __설명__
### ● ex> Memo 생성
1. View에서 사용자를 통해 Memo를 입력받고 Controller에게 넘김
2. Controller는 Model에게 저장 명령
3. Model은 입력받은 Memo를 저장</br>
![](https://github.com/Lee-KyungSeok/MemoExample/blob/master/picture/mvcCreate.png)

### ● 저장되는 Memo 객체
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

### ● Model 설명
#### 0. 저장소 생성
* 여러개의 객체를 저장하는 list 생성
```java
ArrayList<Memo> list = new ArrayList<>();
```

#### 1. create
* 글 하나를 저장한 메모리를 저장소로 이동
```java
public void create(Memo memo) {
  //글번호
  memo.no = lastIndex++;
  // 글 하나를 저장한 메모리를 저장소로 이동
  list.add(memo);
}
```

#### 2. read
* 글번호를 받아 특정 memo Search
* 존재하지 않을 경우 null 값 반환
```java
public Memo read(int no) {
  // no에 맞는 memo 찾기
  for(Memo memo : list) {
    if(memo.no == no) {

      return memo;
    }
  }
  return null;
}
```
#### 3. update
* 글번호를 받아 특정 memo Search
* 특정 memo에 글 하나를 저장한 메모리를 덮어씌워 수정
* 수정되면 true 값을 반환 (수정 확인 용도)
```java
public boolean update(int no, Memo memoTemp) {		
  boolean check=false;
  //글번호를 받아 특정 memo Search
  for(Memo memo : list) {
    if(memo.no == no) {
      //특정 memo에 글 하나를 저장한 메모리를 덮어씌워 수정
      memo.name = memoTemp.name;
      memo.content = memoTemp.content;
      check=true;
      break;
    }
  }
  return check;
}
```
#### 4. delete
* 글번호를 받아 특정 memo Search
* 발견한 특정 memo 삭제
* 삭제되면 true 값을 반환 (수정 확인 용도)
```java
public boolean delete(int no) {
  boolean check = false;
  for(Memo memo : list) {
    if(memo.no == no) {
      list.remove(memo); // ArrayList의 아이템을 삭제하기 위해서 객체를 직접 전달
      check = true;
      break;
    }
  }
  return check;
}
```
#### 5. showList
* 저장되어 있는 모든 Memo 반환
```java
public ArrayList<Memo> showList() {
  return list;
}
```


### ● View 설명

### ● Controller 설명

__소스 코드__
``` java

```
