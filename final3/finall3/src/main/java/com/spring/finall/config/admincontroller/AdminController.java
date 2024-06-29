package com.spring.finall.config.admincontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.finall.adminRepository.MemberRepository;
import com.spring.finall.adminRepository.PersonRepository;
import com.spring.finall.adminRepository.SchoolRepository;
import com.spring.finall.adminRepository.StudentRepository;
import com.spring.finall.adminService.ProductService;
import com.spring.finall.adminService.ReserveinfoService;
import com.spring.finall.adminService.SongService;
import com.spring.finall.admindomain.Student;
import com.spring.finall.admindto.PersonDTO;
import com.spring.finall.admindto.ReponseReserveinfoDTO;
import com.spring.finall.admindto.RequestProductDTO;
import com.spring.finall.admindto.ResSongDTO;
import com.spring.finall.admindto.ResponseProductDTO;
import com.spring.finall.config.Service.HelloService;
import com.spring.finall.config.repository.AdminRepository;
import com.spring.finall.config.testEntity.BookEntity;

//import com.spring.finall.config.repository.AdminRepository;
//import com.spring.finall.config.testEntity.BookEntity;

@Controller
public class AdminController {

	@Autowired
	ApplicationContext context;

	@Autowired
	AdminRepository bookJpaRepository;

	@Autowired
	SchoolRepository schoolRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	EntityManager em;

	@Autowired
	ReserveinfoService reserveinfoService;

	@Autowired
	ProductService productService;

	@Autowired
	SongService songService;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	MemberRepository memberRepository;

	@RequestMapping(value = "/ResponseEx02.do")
	public void responseBodyMassage(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();

	}

	@RequestMapping(value = "/ResponseEx03.do")
	public void responseBodyMassage3(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = (String) request.getAttribute("token");
		response.addHeader("Authorization", "Bearer " + token);
		// 여기는 개발자 도구로 치면 Headers 탭에서
		// ResponseHeader 텝의 부분을 설정 하는것

		// [status-line]
		response.setStatus(HttpServletResponse.SC_OK);
		// [response-headers]
		response.setHeader("Content-type", "text/plain");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("my-header", "hello");
		// 쿠키는 헤더 영역에 태워 보내야 하나??

		// 여기가 중요 포인트!
		// PrintWriter writer = response.getWriter(); 부분이 body에 담아 응답을 하는것!!
		// 개발자 도구로 치면 탭에서 Response
		PrintWriter writer = response.getWriter();
		writer.println("ok");
		// 이제 응용해 보자 PrintWriter writer = response.getWriter(); 는 body에 담아 보낸느것 이다.
		// JSON 은어떻게 보내야 할까?
		System.out.println("흠 여기서 또 header를 추가하면 doFilter가 아니라 죄다.. 포워딩인디...");

	}

	@RequestMapping(value = "/securitylogout.do")
	@ResponseBody
	public String securitylogout(HttpServletRequest request, HttpServletResponse response) throws IOException {

		System.out.println("겟 매핑");
		return "된겨만겨";

	}
	
	@PostMapping(value ="/securitylogout.do")
	@ResponseBody
	public String securitylogout2(HttpServletRequest request, HttpServletResponse response) throws IOException {

		System.out.println("포스트 매핑");
		return "된겨만겨";

	}
	
	
	
	
	
	
	
	
	
	
	@Transactional
	// 주의! javax.transaction 의 트랜잭셔널 어노테이션 이며 이게 없다면?
	// exception is org.hibernate.LazyInitializationException: failed to lazily
	// initialize a
	// 이런 오류가 뜨는데 해설은 아레 사이트 참조
	// https://velog.io/@chocochip/failed-to-lazily-initialize-a-collection-of-role-%EC%97%90%EB%9F%AC
	@GetMapping("/resoveinfo.do")
	// @PageableDefault(page = 0, size = 10) 기법은 나중에 쿼리스트링 url 요청시 키워드 있으니 참조할것
	public String hello3(Model model) {

		ReponseReserveinfoDTO resdto = reserveinfoService.Showreserveinfo();

		List<ReponseReserveinfoDTO> list = new ArrayList<>();

		list.add(resdto);

		model.addAttribute("reservelist", list);

		return "adminreserveinfo.jsp";
	}

	@Transactional
	@GetMapping("/resoveinfolist.do")
	// @PageableDefault(page = 0, size = 10) 기법은 나중에 쿼리스트링 url 요청시 키워드 있으니 참조할것
	public String hello33(Model model) {

		List<ReponseReserveinfoDTO> list = reserveinfoService.resoveinfolist();
		model.addAttribute("reservelist", list);

		return "adminreserveinfo.jsp";

	}

	// 아작스 통신으로 대답하면 제이쿼리로 또 값 삽입하고 태그 삽입 죽노동 해야함.
//	@Transactional
//	@PostMapping("/betweendate.do")
//	@ResponseBody
//	public List<ReponseReserveinfoDTO> betweendate(HttpServletRequest req ,Model model) throws JsonProcessingException {	
//			
//		String startDate = req.getParameter("startDate");
//		String endDate = req.getParameter("endDate");
//
//		
//		
//		List<ReponseReserveinfoDTO> resdto=reserveinfoService.reserveinfobetweendate(startDate, endDate);
//		model.addAttribute("reservelist", resdto);		
//		
//		return reserveinfoService.reserveinfobetweendate(startDate, endDate);
//
//	}

	// 그냥 model로로 해보자 아작스는 제이쿼리로 데이터받고 값 넣고 태그 만들고 쌩쇼 그리고 css 작업도 또 해야함 쌩쑈를 다 해야암..
	@Transactional
	@GetMapping("/noneajaxbetweendate.do")
	public String noneajaxbetweendate(HttpServletRequest req, Model model) throws JsonProcessingException {

		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		Integer startpage = Integer.parseInt(req.getParameter("startpage"));
		Integer endpage = Integer.parseInt(req.getParameter("endpage"));

		List<ReponseReserveinfoDTO> resdto = reserveinfoService.reserveinfobetweendate(startDate, endDate, startpage,
				endpage);
		System.out.println("과연.. 페이징 버튼 개수도 가져올수 있을지->>" + resdto.get(0).getButtoncount());
		model.addAttribute("reservelist", resdto);

		return "adminreserveinfo.jsp";

	}

	// 그냥 model로로 해보자 아작스는 제이쿼리로 데이터받고 값 넣고 태그 만들고 쌩쇼 그리고 css 작업도 또 해야함 쌩쑈를 다 해야암..
	@Transactional
	@GetMapping("/noneajaxpersonalpage.do")
	public String noneajaxpersonalpage(HttpServletRequest req, Model model) throws JsonProcessingException {

		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		Integer startpage = Integer.parseInt(req.getParameter("startpage"));
		Integer endpage = Integer.parseInt(req.getParameter("endpage"));

		Integer endbutton = Integer.parseInt(req.getParameter("endbutton"));
		Integer startbutton = Integer.parseInt(req.getParameter("startbutton"));
		Integer nextbtn = Integer.parseInt(req.getParameter("nextbtn"));
		Long nextvalue = Long.parseLong(req.getParameter("nextvalue"));
		System.out.println("처음버튼" + startbutton + " 마지막버튼" + endbutton + " 마지막행" + nextbtn);

		List<ReponseReserveinfoDTO> resdto = reserveinfoService.reserveinfopersonalpage(startDate, endDate, startbutton,
				endbutton, endpage, nextbtn, nextvalue, startpage);
		System.out.println("과연.. 페이징 버튼 개수도 가져올수 있을지->>" + resdto.get(0).getButtoncount());
		model.addAttribute("reservelist", resdto);

		return "adminreserveinfo.jsp";

	}

	// 그냥 model로로 해보자 아작스는 제이쿼리로 데이터받고 값 넣고 태그 만들고 쌩쇼 그리고 css 작업도 또 해야함 쌩쑈를 다 해야암..
	@Transactional
	@GetMapping("/noneajaxbenextpage.do")
	public String noneajaxbenextpage(HttpServletRequest req, Model model) throws JsonProcessingException {

		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");

		Integer nextbtn = Integer.parseInt(req.getParameter("nextbtn"));
		Integer nextstartpage = Integer.parseInt(req.getParameter("nextstartpage"));
		Integer endpage = Integer.parseInt(req.getParameter("endpage"));
		Integer reserveinfo_num = Integer.parseInt(req.getParameter("reserveinfo_num"));
		Integer buttoncount = Integer.parseInt(req.getParameter("buttoncount"));
		Integer endbutton = Integer.parseInt(req.getParameter("endbutton"));
		System.out.println("reserveinfo_num 씨발+->>>" + reserveinfo_num);
		System.out.println("씨발 마지막버튼" + endbutton);

		List<ReponseReserveinfoDTO> resdto = reserveinfoService.reserveinfonextpage(startDate, endDate, buttoncount,
				endbutton, nextstartpage, nextbtn, endpage, reserveinfo_num);
		System.out.println("과연.. 페이징 버튼 개수도 가져올수 있을지->>" + resdto.get(0).getButtoncount());
		System.out.println("마지막 버튼->>" + resdto.get(0).getEndbutton());
		model.addAttribute("reservelist", resdto);

		return "adminreserveinfo.jsp";

	}

//상세 조회 시작

	@Transactional
	@GetMapping("/detailbetweendate.do")
	public String detailbetweendate(HttpServletRequest req, Model model) throws JsonProcessingException {

		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		String reservename = req.getParameter("reservename");
		String payment = req.getParameter("payment");
		System.out.println("예약자명 >>> " + reservename);
		System.out.println("지불여부 " + payment);
		List<ReponseReserveinfoDTO> resdto = reserveinfoService.detailbetweendate(startDate, endDate, reservename,
				payment);

		model.addAttribute("detailinfolist", resdto);
		return "admindetailreserveinfo.jsp";

	}

	// 상품테이블에 등록을 시작

	@Transactional
	@GetMapping("/insertproduct.do")
	@ResponseBody
	public Integer insertproduct(HttpServletRequest req, RequestProductDTO reqdto, Model model)
			throws JsonProcessingException {

		Integer product_cod = Integer.parseInt(req.getParameter("product_cod"));
		String product_name = req.getParameter("product_name");
		Integer product_price = Integer.parseInt(req.getParameter("product_price"));
		String product_img = req.getParameter("product_img");
		String product_info = req.getParameter("product_info");
		Integer product_quantity = Integer.parseInt(req.getParameter("product_quantity"));

		// System.out.println("상품코드 :"+product_cod+" 상품이름 :"+product_name+" 상품가격
		// :"+product_price+" 상품이미지 :"+product_img+" 상품 정보 :"+product_info+" 상품 수량
		// :"+product_quantity);

		Integer executerow = productService.insertProduct(reqdto);

		System.out.println("서비스에서 받은거 " + executerow);

		return executerow;

	}

	// 테이에 등록된 상품을 고객들이 보는 홈페이지에 뿌릴지 시작.

	@Transactional
	@GetMapping("/showupdateproduct.do")
	@ResponseBody
	public Integer showupdateproduct(HttpServletRequest req) throws JsonProcessingException {

		Integer product_cod = Integer.parseInt(req.getParameter("product_cod"));

		System.out.println("상품코드 :" + product_cod);

		Integer executerow = productService.showupdateproduct(product_cod);

		System.out.println("서비스에서 받은거 " + executerow);

		return executerow;

	}

//관리자 모드 진입			

	@Transactional
	@GetMapping("/adminmode.do")
	@ResponseBody
	public String adminmode(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException {

		System.out.println("adminmode.do 매핑 확인");
		return "adminhome.jsp";

	}

	// 고객들에게 보여질 상품 노출 관리 페이지
//	@Transactional
//	@GetMapping("/adminproductlist.do")
//	public String adminproductlist(HttpServletRequest req, HttpServletResponse res, Model model)
//			throws JsonProcessingException {
//
//		// 그냥 상품 관리 최초 진입점으로 그냥
//		// 기본 제품굼을 pencile 로 주자.
//		String product_group = "pencile";
//		List<ResponseProductDTO> resdtolist = productService.productlist(product_group);
//
//		model.addAttribute("productService", resdtolist);
//		return "adminproductlist.jsp";
//	}
	
	
	//최초 진입시 파라미터타 없다면 기본 열필 군으로 보여주자.
	@Transactional
	@GetMapping("/adminproductlist22.do")
	public String adminproductlist22(HttpServletRequest req, HttpServletResponse res, Model model)
			throws JsonProcessingException {

		// 그냥 상품 관리 최초 진입점으로 그냥
		// 기본 제품굼을 pencile 로 주자.
		String product_group = req.getParameter("product_group");
		if(product_group==null) {
			product_group = "pencile";
		}
	
		List<ResponseProductDTO> resdtolist = productService.productlist(product_group);

		model.addAttribute("productService", resdtolist);
		return "adminproductlist2.jsp";
	}
	
	
	
	

	// 고객들에게 보여질 상품 노출 관리 페이지
	@Transactional
	@GetMapping("/adminproductlist2.do")
	@ResponseBody
	public String adminproductlist2(HttpServletRequest req, HttpServletResponse res, Model model)
			throws JsonProcessingException {

		// 그냥 상품 관리 최초 진입점으로 그냥
		// 기본 제품굼을 pencile 로 주자.
		String product_group = req.getParameter("product_group");
		List<ResponseProductDTO> resdtolist = productService.productlist(product_group);

		Iterator it = req.getHeaderNames().asIterator();

		while (it.hasNext()) {

			System.out.println(it.hasNext());
		}

		model.addAttribute("productService", resdtolist);
		return "adminproductlist.jsp";

	}

	// 고객들에게 보여질 상품 노출 관리 페이지
	@Transactional
	@GetMapping("/adminproductchoicegroup.do")

	public String adminproductlist3(HttpServletRequest req, HttpServletResponse res, Model model)
			throws JsonProcessingException {

		// 서버사이드라 어쩔수 없이 Parameter로도 받아야함....
		String Authorization = req.getParameter("Authorization");
		System.out.println(" 컨트롤러속 바디 토큰 Authorization " + Authorization);

		String product_group= req.getParameter("product_group");
		System.out.println(" 컨트롤러속 바디 토큰 product_group " + product_group);		
		
		// 그냥 상품 관리 최초 진입점으로 그냥
		// 기본 제품굼을 pencile 로 주자.
	
		List<ResponseProductDTO> resdtolist = productService.productlist(product_group);
		model.addAttribute("productService", resdtolist);

		return "adminproductlist2.jsp";
	}

	// 고객들에게 품절로 보여질 함수
	@Transactional
	@GetMapping("/statuschange.do")
	@ResponseBody
	public Integer statuschange(HttpServletRequest req, HttpServletResponse res, Model model)
			throws JsonProcessingException {
		// 그냥 상품 관리 최초 진입점으로 그냥
		// 기본 제품굼을 pencile 로 주자.
		Integer product_cod = Integer.parseInt(req.getParameter("product_cod"));
		String product_status = req.getParameter("product_status");
		System.out.println("받은 상품 코드는?  " + product_cod + " 변경할 생태로는? " + product_status);
		Integer executerow = productService.statuschange(product_cod, product_status);

		return executerow;

	}

	// 고객들에게 상품을 노출시킬 여부를 따질 함수
	@Transactional
	@PostMapping("/statusopen.do")
	@ResponseBody
	public Integer statusOpen(HttpServletRequest req, HttpServletResponse res, Model model)
			throws JsonProcessingException {
		// 그냥 상품 관리 최초 진입점으로 그냥product_Registration_status
		// 기본 제품굼을 pencile 로 주자.

		Integer product_cod = Integer.parseInt(req.getParameter("product_cod"));
		String product_Registration_status = req.getParameter("product_Registration_status");

		Integer executerow = productService.statusOpen(product_cod, product_Registration_status);

		return executerow;

	}
	
	
	

		// 결제지이후 배송이 이루어졌다면 가재고량을 확정 재고량으로 변경하는 함수
		@Transactional
		@PostMapping("/realproductquantityupdate.do")
		@ResponseBody
		public Integer realproductquantityupdate(HttpServletRequest req, HttpServletResponse res, Model model)
				throws JsonProcessingException {
			// 그냥 상품 관리 최초 진입점으로 그냥product_Registration_status
			// 기본 제품굼을 pencile 로 주자.

			Integer product_cod = Integer.parseInt(req.getParameter("product_cod"));
			Integer product_delivery_quantity = Integer.parseInt(req.getParameter("product_delivery_quantity"));

		
			
			
			//ㅂㄷㅂㄷ update 의 성공값는 null 임..
			Integer executerow = productService.realproductquantityupdate(product_cod, product_delivery_quantity);
			System.out.println("executerow->"+executerow);
			
			if(executerow==null) {
				return 1;
			}else {
				return -1;
			}
		

		}
	
	
	
	
//	//고객들의 결제나 결제 취소 현황을 보며 재고수량을 조절하는 페이지		
//		@Transactional
//		@GetMapping("/inventory.do")
//		public String inventoryManager(HttpServletRequest req, HttpServletResponse res, Model model)
//				throws JsonProcessingException {
//			
//			String product_group=req.getParameter("product_group");
//			
//			System.out.println("product_group->"+product_group);
//			
//			productService.
//
//			return "adminfactory.jsp";
//
//		}
		
	
	
	
	
	

//걍 문법
//그냥 암기하자. 리턴부에 jsp파일을 적으면 아작스가 그 파일 자체가 가는 성질을 이용한다.
	//모델 객체를 저장한다 그뒤 리턴시 dynamictag2.jsp 속에서 저장된 모델객체를 랜더링? 하고
	//아작스 응답의 석세스 함수로 가진뒤 그다음 제이쿼리 html(data) 함수로 삽입하자.
	@GetMapping("/dynamicTagtest.do")
	public String dynamicTagtest(Model model) {		
		List<Object> list = new ArrayList();
		list.add("value1");
		list.add("value2");
		list.add("value3");
		model.addAttribute("list", list);		
		return "dynamictag2.jsp";
	}

//걍 문법
	@Transactional
	@GetMapping("/resoveinfo3.do")
	@ResponseBody
	public Object hello333(PersonDTO personDTO) throws JsonProcessingException {

		// 테스트용 데이터 삽입..
//		 for (int i = 0; i < 10; i++) {
//	            Person person = Person.builder()
//	                    .name("person" + i)
//	                    .build();
//
//	            personRepository.save(person);
//
//	            Song song = Song.builder()
//	                    .person(person)
//	                    .singer("가수" + i)
//	                    .title("제목" + i)
//	                    .build();
//
//	            songRepository.save(song);
//	        }

//		List<Song> songs = songRepository.findAll();
//
//		// when
//		for (Song song : songs) {
//			System.out.println("person의 name에 접근 -> " + song.getPerson().getName());
//		}

//		List<Song> songs = songRepository.findAll();

		// List<Song> songslist=songService.showlist();

//		ObjectMapper objm = new ObjectMapper();
		HashMap<String, Object> mao = songService.showlist();
//				
//				objm.writeValueAsString(mao.get("personlist"));

		return mao.get("personlist");

		// when : song.getPerson().getName() 이렇게 데이터에 접근해야!! 반응을한다.
		// 아래 for 문 주석 처리하면 출력도 않됨..!!
//		for (Song song : songs) {
//			System.out.println("person의 name에 접근 -> " + song.getPerson().getName());
//			System.out.println("person의 name에 접근 -> " + song.getPerson().getId());
//		}

	}

	@Transactional
	@GetMapping("/resoveinfo4.do")
	@ResponseBody
	public Object hello3333(PersonDTO personDTO) throws JsonProcessingException {

		HashMap<String, Object> mao = songService.showlist();
		return mao.get("songslist");

	}

	@Transactional
	@GetMapping("/resoveinfo5.do")
	@ResponseBody
	public Object hello33333() throws JsonProcessingException {

		HashMap<String, Object> mao = songService.showlist();

		return mao;

	}

	@Transactional
	@GetMapping("/resoveinfo6.do")
	@ResponseBody
	public List<ResSongDTO> hello333333() throws JsonProcessingException {

		List<ResSongDTO> slist = songService.showlist2();

		System.out.println("------------------리턴직전구분선-------------------------");
		return slist;

	}

	@Transactional
	// 주의! javax.transaction 의 트랜잭셔널 어노테이션 이며 이게 없다면?
	// exception is org.hibernate.LazyInitializationException: failed to lazily
	// initialize a
	// 이런 오류가 뜨는데 해설은 아레 사이트 참조
	// https://velog.io/@chocochip/failed-to-lazily-initialize-a-collection-of-role-%EC%97%90%EB%9F%AC
	@GetMapping("/test.do")
	// @PageableDefault(page = 0, size = 10) 기법은 나중에 쿼리스트링 url 요청시 키워드 있으니 참조할것
	public void hello4(Model model) {

		Optional<Student> op = studentRepository.findById(1L);
		Student student = op.get();
		// 이유는 모르겠으나 ManyToOne 이 걸려있는 엔티티를 가져온후 출력하면 stack OverFlow 에러가 난다 이유는 모르겠다.
		// 또 이유는 모르겠으나 엄마 클래스의 @toString 제거하니 오류가 사랒,ㅁ
		// 이런문제를 대충 양뱡향 참조 에러라 하는데
		// 아래 사이트 참조
		// https://velog.io/@bbbbooo/JPA-%EC%88%9C%ED%99%98-%EC%B0%B8%EC%A1%B0-%EC%8B%9C-StackOverFlow-java.lang.StackOverflowError
		System.out.println("student->>" + student);
		System.out.println("student 의 엄마 스쿨->>  " + student.getSchool());

	}

}
