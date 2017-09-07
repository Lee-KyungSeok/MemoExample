import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 출력 -> System.out.print
 * 입력 -> Scanner(System.in)
 * 
 * @author Kyung
 *
 */
public class MemoMain {
	
	Model model = new Model();
	View view = new View();
	
	public static void main(String args[]) {
		// 입력을 받아서 처리하는 도구
		Scanner scanner = new Scanner(System.in);
		MemoMain main = new MemoMain();
		
		// 키보드 입력중에 Enter키가 입력될때까지 대기
		//String test = scanner.nextLine(); 
		//System.out.println(test);
		
		String command = "";		
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
			
			// 명령어를 분기처리
			switch(command) {
			case "c":
				Memo cMemo = main.view.create(scanner);
				
				// 메모 데이터에 대한 조작이 필요할 경우 모두 컨트롤러에서 작업한다.
				
				main.model.create(cMemo);
				break;
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
			case "l":
				//전체 데이터를 Model로 부터 받아온다.
				ArrayList<Memo> memoList = main.model.showList();
				//받아온 데이터를 View에 보여준다.
				main.view.showList(memoList);
				break;
			}
		}
		main.view.message(100);

	}
}

// 데이터를 저장하는 저장소를 관리하는 객체
class Model{
	
	// 전체 메모를 저정하는 저장소
	// 여러개의 객체를 저장하는 list가 된다.
	ArrayList<Memo> list = new ArrayList<>();
	
	//마지막 글번호 저장
	int lastIndex = 0;
	
	public void create(Memo memo) {
		//글번호
		memo.no = lastIndex++;
		// 글 하나를 저장한 메모리를 저장소로 이동
		list.add(memo);
	}
	
	public Memo read(int no) {
		// no에 맞는 memo 찾기
		for(Memo memo : list) {
			if(memo.no == no) {
				
				return memo;
			}
		}
		return null;
	}
	
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
	
	public ArrayList<Memo> showList() {
		//저장되어 있는 모든 Memo 반환
		return list; 
		
	}
}

// 화면 입출력을 관리하는 객체
class View{
	
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
		
		//delete 여부 체크
		public void delete(boolean check) {
			if(check==true) {
				println("메모가 성공적으로 삭제되었습니다");
			} else {
				println("메모가 삭제가 실패하였습니다.");
			}
		}
		
		public void showList(ArrayList<Memo> memoList) {
			// ArrayList 저장소를 반복문을 돌면서 한줄씩 출력
			for (Memo memo : memoList) {
				print("No: "+memo.no);
				print(" | Author: "+memo.name);
				println(" | Content: "+memo.content);
			}
		}
		
		public void print(String string) {
			System.out.print(string);
		}
		
		public void println(String string) {
			System.out.println(string);
		}
		
		// 글번호를 입력받아 리턴
		public String findNo(Scanner scanner){
			println("글 번호를 입력하세요");
			String tempNo = scanner.nextLine();
			return tempNo;		
		}
		
		// 특정 번호에 맞게 메시지 전달
		public void message(int num) {
			if(num==0) {
				println("입력한 글 번호가 존재하지 않습니다");
			} else if(num==1) {
				println("숫자만 입력해 주세요.");
			} else if(num==100) {
				println("시스템이 종료되었습니다");
			}
		}
	
}

// 여러개가 들어가야 하므로 class를 설정
// 개별 글 한개를 저장하는 클래스, 게시판 글 하나가 Memo class 한개
class Memo{
	int no;
	String name;
	String content;
	long datetime;
}
