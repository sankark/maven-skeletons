package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	   public String redirect(@RequestParam(value="error", required=false) boolean error, 
				ModelMap model) {
		model.put("error", "");
	      return "redirect:/rest/auth/loginpage.html";
	   }
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	   public String admin() {
	     
	      return "redirect:/rest/admin/ci_admin_home.html";
	   }
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	   public String user() {
	     
	      return "redirect:/rest/pages/ci_user_home.html";
	   }

	/*@RequestMapping(value = "/Stores.htm", method = RequestMethod.GET)
	public String StoreDetails(HttpServletRequest request) {
		return "Stores";
	}

	@RequestMapping(value = "/faults.htm", method = RequestMethod.GET)
	public String getFaultsPage(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("REQUESTSTATUS", "");
		try {
			if (checkUserLoggedin()) {
				if (!isValidUser(request)) {
					return "LoginError";
				} else {
					request.setAttribute("DIVISION",DIVISION) ;
					
					return "Faults";
				}
			} else {
				response.sendRedirect(UserServiceFactory.getUserService()
						.createLoginURL(
								request.getRequestURI().replaceAll(
										"faults.htm", "")));
				return "Login";
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/SaveStores.htm", method = RequestMethod.GET)
	public String saveStoreDetails(HttpServletRequest request) {
		try {
			if (checkUserLoggedin()) {
				if (!isValidUser(request)) {
					return "LoginError";
				}else{
					new StoreDetailsInsertionUtil().uploadStoreDetails();
					request.setAttribute("STATUS", "Users uploaded successfully");
					return "Stores";					
				}
			}else{
				return "LoginError";
			}
		} catch (Exception e) {
			request.setAttribute("STATUS",
					"An error occured while submitting. Please try again later.");
			e.printStackTrace();
		}
		return "Stores";
	}

	@RequestMapping(value = "/Ticketdetails.htm", method = RequestMethod.POST)
	public @ResponseBody String raiseHierarchyTicket(HttpServletRequest request) {
		System.out.println("Raising Tickets" + request.getRequestURI() + "   "
				+ request.getServletPath());
		if ((new WoolworthsBusiness()).constructTicketDetailsFlatVO(request)) {
			request.setAttribute("REQUESTSTATUS", "success-tickets");
			if (null != request.getParameter("fault_page")
					&& request.getParameter("fault_page").equalsIgnoreCase(
							"otherServices")) {
				return "Services";
			} else {
				return "Faults";
			}
		} else {
			request.setAttribute("REQUESTSTATUS", "");
		}
		return "Faults";
	}

	@RequestMapping(value = "/RetrieveTickets.htm", method = RequestMethod.GET)
	public @ResponseBody
	List retrieveTickets(HttpServletRequest request) {
		// response.setContentType("application/json");
		System.out.println("Retrieving Tickets............");
		return new WoolworthsBusiness().retrieveTickets(request);
	}
	
	@RequestMapping(value = "/uploadfile.htm", method = RequestMethod.GET)
	public ResponseEntity<byte[]> uploadGet(HttpServletRequest request) throws IOException {
		// response.setContentType("application/json");
		  byte[] image = new ImageService().getImage();
		  final HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.IMAGE_JPEG);

		    return new ResponseEntity<byte[]>(image, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/FeatureRequest.htm", method = RequestMethod.POST)
	public String featureRequest(HttpServletRequest request) {
		System.out.println("Feature Requests.......");
		try {
			new WoolworthsBusiness().composeFeaturemail(request);
			request.setAttribute("REQUESTSTATUS", "success-feature");
		} catch (Exception e) {
			request.setAttribute("REQUESTSTATUS", "");
			e.printStackTrace();
		}
		if (request.getParameter("fromPage").equalsIgnoreCase("services")) {
			return "Services";
		} else if (request.getParameter("fromPage").equalsIgnoreCase("faults")) {
			return "Faults";
		}
		return "Services";
	}

	@RequestMapping(value = "/FeedbackRequest.htm", method = RequestMethod.POST)
	public String feedbackRequest(HttpServletRequest request) {
		System.out.println("Feedback Requests...........");
		try {
			new WoolworthsBusiness().composeFeedbackMail(request);
			request.setAttribute("REQUESTSTATUS", "success-feedback");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("REQUESTSTATUS", "");
		}
		if (request.getParameter("fromPage").equalsIgnoreCase("services")) {
			return "Services";
		} else if (request.getParameter("fromPage").equalsIgnoreCase("faults")) {
			return "Faults";
		}
		return "Services";
	}

	@RequestMapping(value = "/StoreDetails.htm", method = RequestMethod.GET)
	public @ResponseBody
	List<StoreDetailsVO> storeDetails(HttpServletRequest request) {
		return new WoolworthsBusiness().constructStoreDetails(storedetails,
				request);
	}

	@RequestMapping(value = "/Tickets.htm", method = RequestMethod.GET)
	public String ticketDetails(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("REQUESTSTATUS", "");
		try {
			if (checkUserLoggedin()) {
				if (!isValidUser(request)) {
					return "LoginError";
				} else {
					setTicketDetails(request);
					return "Services";
				}				
			} else {
				response.sendRedirect(UserServiceFactory.getUserService()
						.createLoginURL(request.getRequestURI()));
				return "Services";
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return "Services";
	}
	
	
	@RequestMapping(value = "/UpdateStoreDetails.htm", method = RequestMethod.POST)
	public @ResponseBody String UpdateStoreDetails(@RequestBody String json,HttpServletRequest request,
			HttpServletResponse response) {
						
		UserDetailsVO vo  = cloneMe((UserDetailsVO)request.getSession().getAttribute("USERDETAILS"));
		JSONObject jsonResp = null;
		try {
			System.out.println("%%%%%%%%%%%%%%"+vo.getContactNumber());
			@SuppressWarnings("unchecked")
			HashMap<String,String> result =
			        new ObjectMapper().readValue(json, HashMap.class);
			System.out.println("store id"+result.get("store_id"));
			UserDetailsVO store = new WoolworthsBusiness().retrieveStoreDetailsVO(result.get("store_id"));
			if(store != null)
			{
				vo.setLocation(store.getLocation());
				vo.setStoreID(store.getStoreID());
				vo.setContactNumber(store.getContactNumber());
				result.put("contact_num", store.getContactNumber());
				result.put("location", store.getLocation());
				result.put("store_id", store.getStoreID());
			}
			else
			{
				vo.setLocation(result.get("location").trim());
				vo.setStoreID(result.get("store_id").trim());
				vo.setContactNumber(result.get("contact_num").trim());
			}
			System.out.println("store"+store);
			vo.setLocation(store.getLocation());
			vo.setStoreID(store.getStoreID());
			result.put("location", store.getLocation());
			String contact_num = result.get("contact_num").trim();
			System.out.println("$$$$$$$$"+contact_num);
			if(contact_num.equalsIgnoreCase(vo.getContactNumber().trim()))
			{
				result.put("contact_num", store.getContactNumber());
				vo.setContactNumber(store.getContactNumber());
			}
			else
			{
				vo.setContactNumber(contact_num);
				result.put("contact_num",contact_num);
			}
			request.getSession().setAttribute("USERDETAILS",vo);
			//System.out.println("%%%%%%%%%%%%%%"+ result.get("contact_num"));
			jsonResp = new JSONObject(result);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResp.toString();
	}
	
	private UserDetailsVO cloneMe(UserDetailsVO vo) {
		UserDetailsVO newVo = new UserDetailsVO();
		newVo.setContactName(vo.getContactName());
		newVo.setContactNumber(vo.getContactNumber());
		newVo.setDivision(vo.getDivision());
		newVo.setLocation(vo.getLocation());
		newVo.setLoginID(vo.getLoginID());
		newVo.setStoreID(vo.getStoreID());
		return newVo;
		// TODO Auto-generated method stub
		
	}

	@RequestMapping(value = "/GetRequiredInformation.htm", method = RequestMethod.POST)
	public String GetRequiredInformation(@RequestBody String json,HttpServletRequest request,
			HttpServletResponse response) {
		@SuppressWarnings("unchecked")
		HashMap<String, String> result=null;
		try {
			result = new ObjectMapper().readValue(json, HashMap.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String view;
		if((view = result.get("issue"))!=null && result.get("default_cat").equalsIgnoreCase("DefaultMerchandise")){
			view = view.replace(" ", "").toLowerCase();
			view = view.replace("/", "").toLowerCase();
			view = ConfigurationService.getInstance().getMapping().getProperty(view);
			return view;
		}
		if((view = result.get("issue"))!=null && result.get("default_cat").equalsIgnoreCase("DefaultBigW")){
			String fault = result.get("fault").replace(" ", "").toLowerCase();
			view = view.replace(" ", "").toLowerCase();
			view = view.replace("/", "").toLowerCase();
			view = ConfigurationService.getInstance().getMapping().getProperty(fault+"_"+view);
			return view;
		}
		//System.out.println("****************"+result.get("fault").trim().toLowerCase());
		else if((view = ConfigurationService.getInstance().getMapping().getProperty(result.get("fault").trim().toLowerCase()))!=null)
			return view;

		else if((view = result.get("default_cat"))!=null)
			return view;
		return "DefaultHardware";
	}

	private static List<StoreDetailsVO> getStoreDetails() {
		List<StoreDetailsVO> storeslist = new ArrayList<StoreDetailsVO>();
		ObjectDatastore datastore = new AnnotationObjectDatastore();
		Iterator<StoreDetailsVO> stores = datastore.find()
				.type(StoreDetailsVO.class).now();
		int i = 0;
		while (stores.hasNext()) {
			StoreDetailsVO svo = stores.next();
			storeslist.add(svo);
		}
		return storeslist;
	}

	private boolean isValidUser(HttpServletRequest request) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		try {
			if (user != null) {
				String domainName = user.getEmail().split("@")[1];
				if (null != domainName) {
					Properties prop = new Properties();
					InputStream propinstream = this.getClass()
							.getResourceAsStream("/data.properties");
					prop.load(propinstream);
					String domainsList = prop.getProperty("DOMAINNAME");
					String usersList = "";
					String tfslist = prop.getProperty("TFSUSERSNAME");
					String petrollist = prop.getProperty("PETROLUSERSNAME");
					String bigWList = prop.getProperty("BIGWUSERSNAME");
					UserDetailsVO vo = (UserDetailsVO)request.getSession().getAttribute("USERDETAILS");
					if(null == vo){
						vo = new WoolworthsBusiness().retrieveUserDetailsVO(user.getEmail().toLowerCase());						
					}
					System.out.println("!)))))))))))"+vo.getDivision());
					if(null != vo.getDivision() && vo.getDivision().trim()!="" && (vo.getDivision().equalsIgnoreCase("Petrol") || vo.getDivision().equalsIgnoreCase("Supermarket"))){
						if(vo.getDivision().equalsIgnoreCase("Petrol")){
							usersList = petrollist;
						}else if(vo.getDivision().equalsIgnoreCase("Big W")){
							usersList = bigWList;
						}else{
							usersList = tfslist;
						}
					}else{
						System.out.println("Inside else");
						if(petrollist.contains(user.getEmail().toLowerCase())){
							usersList = petrollist;
							vo.setDivision("Petrol");
						}else if(bigWList.contains(user.getEmail().toLowerCase())){
							usersList = bigWList;
							vo.setDivision("Big W");
						}else{
							usersList = tfslist;
							vo.setDivision("Supermarket");
						}
					}
					request.getSession().setAttribute("USERDETAILS",vo);
					System.out.println("!)))))))))))"+usersList);
					if (domainsList.contains(domainName.toLowerCase())) {
						return true;
					}else if(usersList.contains(user.getEmail().toLowerCase())){
						return true;
					}						
					else {
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean isAdmin(String user) {
		if (user == null) {
				System.out.println("User not Logged in or Cookie not set");
				return false;
			} else {
				if(ConfigurationService.getInstance().getAdminUsers().contains(user))
				return true;
			}

		return false;
	}

	private boolean checkUserLoggedin() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		try {
			if (user == null) {
				System.out.println("User not Logged in or Cookie not set");
				return false;
			} else {
				System.out
						.println("User Logged in and Cookie is set for user with value"
								+ user.getEmail());
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void setTicketDetails(HttpServletRequest request) {
		UserService userService = UserServiceFactory.getUserService();
		String name = "";
		if (null != userService) {
			name = userService.getCurrentUser().getEmail().toLowerCase();
		}
		System.out.println("User ID is " + name + " Some Change");
		UserDetailsVO vo = (UserDetailsVO)request.getSession().getAttribute("USERDETAILS");
		if(null == vo){
			vo = new WoolworthsBusiness().retrieveUserDetailsVO(name);
			request.getSession().setAttribute("USERDETAILS",vo);
		}
		DIVISION = vo.getDivision();
		//DIVISION = "Petrol";
		System.out.println(vo.getContactNumber() + vo.getDivision());
		request.getSession().setAttribute("USERDETAILS", vo);
		if(isAdmin(name))
		request.getSession().setAttribute("isAdmin", "true");
		else
		request.getSession().setAttribute("isAdmin", "false");
		request.setAttribute("DIVISION", DIVISION);
		System.out.println("Getting to Tickets Page....." + name);
		if (vo != null)
			System.out.println("Getting to Tickets Page.....  "
					+ vo.getLoginID() + "  " + vo.getStoreID());
	}*/
}
