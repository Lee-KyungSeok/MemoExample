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
### ● ex> Memo 생성의 경우
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
  //글번호를 받아 특정 memo Search
  for(Memo memo : list) {
    if(memo.no == no) {
      //발견한 특정 memo 삭제
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
  //저장되어 있는 모든 Memo 반환
  return list;
}
```


### ● View 설명

#### 1. create
* 저장할 memo 공간 확보
* 저장할 memo 입력 후 전달
```java
// 키보드 입력을 받는 함수
  public Memo create(Scanner scanner) {
    // 글 하나를 저장하기 위한 메모리 공간 확보
    Memo memo = new Memo();

    println("이름을 입력하세요 : ");
    memo.name = scanner.nextLine();
    println("내용을 입력하세요 : ");
    memo.content = scanner.nextLine();

    // 날짜는 자동으로 입력(시스템 시간)
    memo.datetime = System.currentTimeMillis();

    return memo;
  }
```

#### 2. read
* 입력받은 메모 내용 출력
* 시간은 포맷에 맞게 출력
```java
public void read(Memo memo) {
  // 입력받은 메모에 저장된 값 출력
  println("No: "+memo.no);
  println("Author: "+memo.name);
  println("Content: "+memo.content);

  //숫자로 입력받은 날짜값을 실제 날짜로 변경
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  String formattedDate = sdf.format(memo.datetime);
  println(formattedDate);		
}
```

#### 3. update
* 특정 memo를 받아와 update될 정보만 수정
* 수정된 memo를 반환
* 수정 완료사항 체크
```java
//새로운 메모리 저장
public Memo update(Memo memo, Scanner scanner) {

  Memo memoTemp = new Memo();
  // 업데이트 할 글을 새로운 메모리에 저장
  println("이름을 입력해주세요: ");
  memo.name = scanner.nextLine();
  println("내용을 입력해주세요: ");
  memo.content = scanner.nextLine();
  memoTemp = memo;

  return memoTemp;
}
//update여부 체크
public void update(boolean check) {
  if(check==true) {
    println("메모가 성공적으로 수정되었습니다");
  } else {
    println("메모가 수정이 실패하였습니다.");
  }
}
```

#### 4. delete
* 삭제 여부 체크
```java
//delete 여부 체크
public void delete(boolean check) {
  if(check==true) {
    println("메모가 성공적으로 삭제되었습니다");
  } else {
    println("메모가 삭제가 실패하였습니다.");
  }
}
```

#### 5. showList
* 전체 Memo를 받아 모두 출력
```java
public void showList(ArrayList<Memo> memoList) {
  // ArrayList 저장소를 반복문을 돌면서 한줄씩 출력
  for (Memo memo : memoList) {
    print("No: "+memo.no);
    print(" | Author: "+memo.name);
    println(" | Content: "+memo.content);
  }
}
```

#### 6. print / println
* 출력함수 모음
```java
public void print(String string) {
  System.out.print(string);
}

public void println(String string) {
  System.out.println(string);
}
```

#### 7. findNo
* 특정 번호를 입력받음
```java
// 글번호를 입력받아 리턴
public String findNo(Scanner scanner){
  println("글 번호를 입력하세요");
  String tempNo = scanner.nextLine();
  return tempNo;		
}
```

#### 8. message
* 특정 메시지 반환
```java
public void message(int num) {
  if(num==0) {
    println("입력한 글 번호가 존재하지 않습니다");
  } else if(num==1) {
    println("숫자만 입력해 주세요.");
  } else if(num==100) {
    println("시스템이 종료되었습니다");
  }
}
```

### ● Controller 설명

#### 0. 명령어 입력
```java
while(!command.equals("exit")) {

  // 명령어를 입력받아서 후속처리
  // c - create : 데이터 입력모드로 전환
  // r - read : 데이터 읽기모드로 전환
  // u - update : 데이터 수정모드로 전환
  // d - delete : 데이터 삭제모드로 전환
  main.view.println("------------명령어를 입력하세요-------------");
  main.view.println("c : 쓰기, r : 읽기, u : 수정, d : 삭제, l : 목록");
  main.view.println("exit : 종료");
  main.view.println("--------------------------------------");
  command = scanner.nextLine();
        /* 코드 생략 */
}
```
#### 1. create
* View에서 입력받은 Memo를 Model로 넘겨 저장소에 저장
```java
switch(command) {
case "c":
  Memo cMemo = main.view.create(scanner);
  main.model.create(cMemo);
  break;
/* 코드 생략 */
}
```

#### 2. read
* View에서 번호를 입력받아 이것이 int형인지 확인
* int형이 아니라면 오류처리
* 입력받은 번호를 통해 Model로 부터 데이터 수신 요청
* 데이터가 존재하지 않는다면 찾을 수 없다는 메시지 View에 출력 요청
* 데이터가 존재한다면 받은 데이터 View에 출력 요청
```java
switch(command) {
/* 코드 생략 */
case "r":
  String rTempNo = main.view.findNo(scanner);
  // ------ 숫자가 입력되지 않았을 때의 예외 처리 ------//
  try {
    int no = Integer.parseInt(rTempNo);
    //Model에서 no를 받아 데이터를 불러온다.
    Memo rMemo = main.model.read(no);

    //no가 없는 경우 메세지를 보여준다.
    if(rMemo == null) {
      main.view.message(0);
    } else {
      // 데이터 memo를 화면에 보여준다.
      main.view.read(rMemo);
    }
  } catch(NumberFormatException e) {
    main.view.message(1);
  }				
  break;
/* 코드 생략 */
}
```

#### 3. update
* View에서 번호를 입력받아 이것이 int형인지 확인
* int형이 아니라면 오류처리
* 입력받은 번호를 통해 Model로 부터 데이터 수신 요청
* 데이터가 존재하지 않는다면 찾을 수 없다는 메시지 View에 출력 요청
* 데이터가 존재한다면 받은 데이터에 대한 수정사항  View에 입력 요청
* 수정된 Memo를 Model에 전달하여 저장된 내용 수정 요청
* 수정 완료 여부 메시지 View에 출력 요청
```java
switch(command) {
/* 코드 생략 */
case "u":
  String uTempNo = main.view.findNo(scanner);
  // ------ 숫자가 입력되지 않았을 때의 예외 처리 ------//
  try {
    int no = Integer.parseInt(uTempNo);
    //Model에서 no를 받아 데이터를 불러온다.
    Memo uMemo = main.model.read(no);

    //no가 없는 경우 메세지를 보여준다.
    if(uMemo ==null) {
      main.view.message(0);
    } else {
      // 데이터의 수정사항을 받는다.
      uMemo = main.view.update(uMemo,scanner);
      // 수정사항을 Model에 전달하고 수정 완료여부를 받아온다.
      boolean updateCheck = main.model.update(no, uMemo);
      // 수정이 완료여부를 사용자에게 알린다.
      main.view.update(updateCheck);
    }
  } catch(NumberFormatException e) {
    main.view.message(1);
  }		
  break;
/* 코드 생략 */
}
```

#### 4. delete
* View에서 번호를 입력받아 이것이 int형인지 확인
* int형이 아니라면 오류처리
* 입력받은 번호를 통해 Model로 부터 데이터 수신 요청
* 데이터가 존재하지 않는다면 찾을 수 없다는 메시지 View에 출력 요청
* 데이터가 존재한다면 Model에게 데이터에 대한 삭제 요청
* 삭제 완료 여부 메시지 View에 출력 요청
```java
switch(command) {
/* 코드 생략 */
case "d":
  String dTempNo = main.view.findNo(scanner);
  // ------ 숫자가 입력되지 않았을 때의 예외 처리 ------//
  try {
    int no = Integer.parseInt(dTempNo);
    //Model에서 no를 받아 데이터를 불러온다.
    Memo dMemo = main.model.read(no);

    //no가 없는 경우 메세지를 보여준다.
    if(dMemo ==null) {
      main.view.message(0);
    } else {
      // 데이터 memo를 삭제하고 완료 여부를 알린다.
      boolean deleteCheck = main.model.delete(no);
      // 데이터 삭제여부를 사용자에게 알린다.
      main.view.delete(deleteCheck);
    }
  } catch(NumberFormatException e) {
    main.view.message(1);
  }
  break;
/* 코드 생략 */
}
```

#### 5. showList
* 전체 Memo 데이터를 Model로부터 추출
* 모든 데이터 View에 출력 요청
```java
switch(command) {
/* 코드 생략 */
case "l":
  //전체 데이터를 Model로 부터 받아온다.
  ArrayList<Memo> memoList = main.model.showList();
  //받아온 데이터를 View에 보여준다.
  main.view.showList(memoList);
  break;
}
```

## __전체 소스 코드 링크__
### [코드 보기](https://github.com/Lee-KyungSeok/MemoExample/blob/master/src/MemoMain.java)
