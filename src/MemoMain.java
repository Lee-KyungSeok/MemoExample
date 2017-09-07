import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ��� -> System.out.print
 * �Է� -> Scanner(System.in)
 * 
 * @author Kyung
 *
 */
public class MemoMain {
	
	Model model = new Model();
	View view = new View();
	
	public static void main(String args[]) {
		// �Է��� �޾Ƽ� ó���ϴ� ����
		Scanner scanner = new Scanner(System.in);
		MemoMain main = new MemoMain();
		
		// Ű���� �Է��߿� EnterŰ�� �Էµɶ����� ���
		//String test = scanner.nextLine(); 
		//System.out.println(test);
		
		String command = "";		
		while(!command.equals("exit")) {
			
			// ��ɾ �Է¹޾Ƽ� �ļ�ó��
			// c - create : ������ �Է¸��� ��ȯ
			// r - read : ������ �б���� ��ȯ
			// u - update : ������ �������� ��ȯ
			// d - delete : ������ �������� ��ȯ
			main.view.println("------------��ɾ �Է��ϼ���-------------");
			main.view.println("c : ����, r : �б�, u : ����, d : ����, l : ���");
			main.view.println("exit : ����");
			main.view.println("--------------------------------------");
			command = scanner.nextLine();
			
			// ��ɾ �б�ó��
			switch(command) {
			case "c":
				Memo cMemo = main.view.create(scanner);
				
				// �޸� �����Ϳ� ���� ������ �ʿ��� ��� ��� ��Ʈ�ѷ����� �۾��Ѵ�.
				
				main.model.create(cMemo);
				break;
			case "r":
				String rTempNo = main.view.findNo(scanner);
				// ------ ���ڰ� �Էµ��� �ʾ��� ���� ���� ó�� ------//
				try {
					int no = Integer.parseInt(rTempNo);
					//Model���� no�� �޾� �����͸� �ҷ��´�.
					Memo rMemo = main.model.read(no);
					
					//no�� ���� ��� �޼����� �����ش�.
					if(rMemo == null) {
						main.view.message(0);
					} else {
						// ������ memo�� ȭ�鿡 �����ش�.
						main.view.read(rMemo);
					}
				} catch(NumberFormatException e) {
					main.view.message(1);
				}				
				break;
			case "u":
				String uTempNo = main.view.findNo(scanner);
				// ------ ���ڰ� �Էµ��� �ʾ��� ���� ���� ó�� ------//
				try {
					int no = Integer.parseInt(uTempNo);
					//Model���� no�� �޾� �����͸� �ҷ��´�.
					Memo uMemo = main.model.read(no);
					
					//no�� ���� ��� �޼����� �����ش�.
					if(uMemo ==null) {
						main.view.message(0);
					} else {
						// �������� ���������� �޴´�.
						uMemo = main.view.update(uMemo,scanner);
						// ���������� Model�� �����ϰ� ���� �ϷῩ�θ� �޾ƿ´�.
						boolean updateCheck = main.model.update(no, uMemo);
						// ������ �ϷῩ�θ� ����ڿ��� �˸���.
						main.view.update(updateCheck);
					}
				} catch(NumberFormatException e) {
					main.view.message(1);
				}		
				
				break;
			case "d":
				String dTempNo = main.view.findNo(scanner);
				// ------ ���ڰ� �Էµ��� �ʾ��� ���� ���� ó�� ------//
				try {
					int no = Integer.parseInt(dTempNo);
					//Model���� no�� �޾� �����͸� �ҷ��´�.
					Memo dMemo = main.model.read(no);
					
					//no�� ���� ��� �޼����� �����ش�.
					if(dMemo ==null) {
						main.view.message(0);
					} else {
						// ������ memo�� �����ϰ� �Ϸ� ���θ� �˸���.
						boolean deleteCheck = main.model.delete(no);
						// ������ �������θ� ����ڿ��� �˸���.
						main.view.delete(deleteCheck);
					}
				} catch(NumberFormatException e) {
					main.view.message(1);
				}
				
				break;
			case "l":
				//��ü �����͸� Model�� ���� �޾ƿ´�.
				ArrayList<Memo> memoList = main.model.showList();
				//�޾ƿ� �����͸� View�� �����ش�.
				main.view.showList(memoList);
				break;
			}
		}
		main.view.message(100);

	}
}

// �����͸� �����ϴ� ����Ҹ� �����ϴ� ��ü
class Model{
	
	// ��ü �޸� �����ϴ� �����
	// �������� ��ü�� �����ϴ� list�� �ȴ�.
	ArrayList<Memo> list = new ArrayList<>();
	
	//������ �۹�ȣ ����
	int lastIndex = 0;
	
	public void create(Memo memo) {
		//�۹�ȣ
		memo.no = lastIndex++;
		// �� �ϳ��� ������ �޸𸮸� ����ҷ� �̵�
		list.add(memo);
	}
	
	public Memo read(int no) {
		// no�� �´� memo ã��
		for(Memo memo : list) {
			if(memo.no == no) {
				
				return memo;
			}
		}
		return null;
	}
	
	public boolean update(int no, Memo memoTemp) {		
		boolean check=false;
		//�۹�ȣ�� �޾� Ư�� memo Search
		for(Memo memo : list) {
			if(memo.no == no) {
				//Ư�� memo�� �� �ϳ��� ������ �޸𸮸� ����� ����
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
		//�۹�ȣ�� �޾� Ư�� memo Search
		for(Memo memo : list) {
			if(memo.no == no) {
				//�߰��� Ư�� memo ����
				list.remove(memo); // ArrayList�� �������� �����ϱ� ���ؼ� ��ü�� ���� ����
				check = true;
				break;
			}
		}
		return check;
	}
	
	public ArrayList<Memo> showList() {
		//����Ǿ� �ִ� ��� Memo ��ȯ
		return list; 
		
	}
}

// ȭ�� ������� �����ϴ� ��ü
class View{
	
	// Ű���� �Է��� �޴� �Լ�
		public Memo create(Scanner scanner) {
			// �� �ϳ��� �����ϱ� ���� �޸� ���� Ȯ��
			Memo memo = new Memo();
			
			println("�̸��� �Է��ϼ��� : ");
			memo.name = scanner.nextLine();
			println("������ �Է��ϼ��� : ");
			memo.content = scanner.nextLine();
			
			// ��¥�� �ڵ����� �Է�(�ý��� �ð�)
			memo.datetime = System.currentTimeMillis();
			
			return memo;
		}
		
		public void read(Memo memo) {
			// �Է¹��� �޸� ����� �� ���
			println("No: "+memo.no);
			println("Author: "+memo.name);
			println("Content: "+memo.content);
			
			//���ڷ� �Է¹��� ��¥���� ���� ��¥�� ����
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String formattedDate = sdf.format(memo.datetime);
			println(formattedDate);		
		}
		//���ο� �޸� ����
		public Memo update(Memo memo, Scanner scanner) {
			
			Memo memoTemp = new Memo();
			// ������Ʈ �� ���� ���ο� �޸𸮿� ����
			println("�̸��� �Է����ּ���: ");
			memo.name = scanner.nextLine();
			println("������ �Է����ּ���: ");
			memo.content = scanner.nextLine();
			memoTemp = memo;

			return memoTemp;
		}
		//update���� üũ
		public void update(boolean check) {
			if(check==true) {
				println("�޸� ���������� �����Ǿ����ϴ�");
			} else {
				println("�޸� ������ �����Ͽ����ϴ�.");
			}
		}
		
		//delete ���� üũ
		public void delete(boolean check) {
			if(check==true) {
				println("�޸� ���������� �����Ǿ����ϴ�");
			} else {
				println("�޸� ������ �����Ͽ����ϴ�.");
			}
		}
		
		public void showList(ArrayList<Memo> memoList) {
			// ArrayList ����Ҹ� �ݺ����� ���鼭 ���پ� ���
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
		
		// �۹�ȣ�� �Է¹޾� ����
		public String findNo(Scanner scanner){
			println("�� ��ȣ�� �Է��ϼ���");
			String tempNo = scanner.nextLine();
			return tempNo;		
		}
		
		// Ư�� ��ȣ�� �°� �޽��� ����
		public void message(int num) {
			if(num==0) {
				println("�Է��� �� ��ȣ�� �������� �ʽ��ϴ�");
			} else if(num==1) {
				println("���ڸ� �Է��� �ּ���.");
			} else if(num==100) {
				println("�ý����� ����Ǿ����ϴ�");
			}
		}
	
}

// �������� ���� �ϹǷ� class�� ����
// ���� �� �Ѱ��� �����ϴ� Ŭ����, �Խ��� �� �ϳ��� Memo class �Ѱ�
class Memo{
	int no;
	String name;
	String content;
	long datetime;
}
