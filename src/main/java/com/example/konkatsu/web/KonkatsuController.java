

package com.example.konkatsu.web;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.konkatsu.domain.Profile;
import com.example.konkatsu.repository.FavoriteRepository;
import com.example.konkatsu.service.LoginUserDetails;
import com.example.konkatsu.service.ProfileService;


@Controller
//「URL」の接頭辞をkonkatsuに設定(このクラスで呼ばれたpostなどは/konkatsu/(path)となる)
@RequestMapping("konkatsu")

public class KonkatsuController {
	@Autowired
	ProfileService profileService;

	@Autowired
	FavoriteRepository favoriteRepository;


	@ModelAttribute  //@ModelAttributeを付けたメソッド内でProfileFormを初期化
	//@ModelAttributeがついたメソッドは、@PostMappingでマッピングされたメソッドの前に実行され
	  //「返り値」は自動で「Model」に追加される（この例だと「list」や「create」メソッドが呼ばれる前に「model.addAttribute(new ProfileForm())」相当の処理が行われる）
	 ProfileForm setUpForm() {
	        return new ProfileForm();
	 }


	@GetMapping   //pathの指定がない場合は URL は/konkatsu
	public String myPage(@AuthenticationPrincipal LoginUserDetails userDetails) {
        return "konkatsu/myPage";
    }



	@GetMapping(path = "profileForm")   //URL は/konkatsu/profileFormとなる
	String list(Model model){  //SpringMVCでは画面に値を渡す為にModelオブジェクトを使用
		List<Profile> profile = profileService.findAll();
		model.addAttribute("profile", profile);  //第一引数はThymeleafで取り出す時に使う名前、第二引数はThymeleafに渡したいオブジェクトを指定
		return "konkatsu/profileForm";   //遷移する画面の名前
	}


	//th:action="@{/konkatsu/createProfile}" の処理
	@PostMapping(path = "createProfile")   //URLが  /konkatsu/createProfile　となる
	public String create(@Validated ProfileForm form, BindingResult bindingResult, Model model,
			// 送信されたフォームの入力チェックを行う為に@Validatedアノテーションを付ける。
			// これによりProfileFormに設定したBean Validationアノテーションが評価され、結果が隣の引数のBindingResultに格納される
			@AuthenticationPrincipal LoginUserDetails userDetails) throws IOException{
		//@AuthenticationPrincipalをつけることでログイン中の[LoginUserDetails]オブジェクトを取得できる

		System.out.println(form.getId());
		System.out.println(form.getName());
		System.out.println(form.getGenderId());
		System.out.println(form.getBirthday());
		System.out.println(form.getHeight());
		System.out.println(form.getOccupationId());
		System.out.println(form.getIncome());
		System.out.println(form.getText());
		System.out.println(form.getFile());

		if (bindingResult.hasErrors()){    //入力チェックの結果を確認し、エラーがある場合は一覧画面表示に戻る
			System.out.println("エラー");
			return list(model);
		}


		Profile profile = new Profile();
		profile.setId(form.getId());
		profile.setName(form.getName());
		profile.setGenderId(form.getGenderId());
		profile.setBirthday(form.getBirthday());
		profile.setHeight(form.getHeight());
		profile.setOccupationId(form.getOccupationId());
		profile.setIncome(form.getIncome());
		profile.setText(form.getText());
		MultipartFile uploaded = form.getFile();
		byte[] image = uploaded.getBytes();   //アップロードファイルをbyte配列で取得
		profile.setImage(image);

		BeanUtils.copyProperties(form, profile);//ProfileFormをProfileにコピーする
		profileService.create(profile, userDetails.getUser());   //ProfileをDBに追加する　　（ログインユーザー情報も）

		return "/konkatsu/profileConfirm";

	}

	@GetMapping(path = "usersList")   //pathの指定がない場合は URL は/konkatsu
	String usersList(@RequestParam Integer genderId, @PageableDefault (size=3) Pageable pageable, Model model){
		Page<Profile> profiles = profileService.findUsers(genderId, pageable);

		//<ProfileForView>に<Profile>の値を代入処理を繰り返し
		List<ProfileForView> profilesForView = new ArrayList<ProfileForView>();
		for(Profile profile: profiles){
			ProfileForView profileForView = new ProfileForView(profile);
			profilesForView.add(profileForView);
		}

//      上記処理を簡潔化
//		List<ProfileForView> profilesForView = profiles.stream().map(
//				profile -> new ProfileForView(profile)).collect(Collectors.toList());

//		//上記処理をさらに簡潔化
//		List<ProfileForView> profilesForView = profiles.stream().map(
//				ProfileForView::new).collect(Collectors.toList());

		model.addAttribute("page", profiles);
		model.addAttribute("profiles", profilesForView);
		return "konkatsu/profileList";   //遷移する画面の名前
	}

	@GetMapping(path = "myProfile")   //pathの指定がない場合は URL は/konkatsu
	String MyList(@RequestParam Integer id, Model model){  //SpringMVCでは画面に値を渡す為にModelオブジェクトを使用
		Profile profile = profileService.findProfile(id);

		//第一引数はThymeleafで取り出す時に使う名前、第二引数はThymeleafに渡したいオブジェクトProfileForViewを指定
		model.addAttribute("profile", new ProfileForView(profile));
		return "konkatsu/myProfile";   //遷移する画面の名前
	}

	//検索したユーザープロフィール表示
	@GetMapping(path = "searchProfiles")
	String searchProfiles(@RequestParam String searchName, @RequestParam Integer genderId, @PageableDefault (size=3) Pageable pageable, Model model){
		Page<Profile> profiles = profileService.findProfiles(searchName, genderId ,pageable);

			List<ProfileForView> profilesForView = new ArrayList<ProfileForView>();
			for(Profile profile: profiles){
				ProfileForView profileForView = new ProfileForView(profile);
				profilesForView.add(profileForView);
				}

			model.addAttribute("page", profiles);
			model.addAttribute("profiles", profilesForView);
		return "konkatsu/searchProfileList";   //遷移する画面の名前
	}


	//お気に入りに追加する
	@PostMapping(path = "createFavorite")
	 String create(@RequestParam Integer userId, @RequestParam Integer favoriteUserId) {
		favoriteRepository.createFavorite(userId, favoriteUserId);
		return "/konkatsu/favoriteConfirm";
	}

	//お気に入りを取り消す
	@PostMapping(path = "deleteFavorite")
	 String delete(@RequestParam Integer userId, @RequestParam Integer favoriteUserId) {
		favoriteRepository.deleteFavorite(userId, favoriteUserId);
		return "/konkatsu/deleteFavoriteConfirm";
	}


	//お気に入り一覧
	@GetMapping(path = "favoriteList")
	String favoriteList(@RequestParam Integer userId, @PageableDefault (size=3) Pageable pageable
			, Model model){
		Page<Profile> profiles = profileService.findFavorites(userId, pageable);

		List<ProfileForView> profilesForView = new ArrayList<ProfileForView>();
		for(Profile profile: profiles){
			ProfileForView profileForView = new ProfileForView(profile);
			profilesForView.add(profileForView);
			}

		model.addAttribute("page", profiles);
		model.addAttribute("profiles", profilesForView);
		return "konkatsu/favoriteList";
	}



	//View用のクラス
	public static class ProfileForView {
		public Integer id;
		public String name;
		public Integer genderId;
		public String gender;
		public Date birthday;
		public Integer height;
		public String occupationName;
		public Integer income;
		public String text;
		public String mail;
		public String image;

		//profileの値を代入
		public ProfileForView(Profile profile){
			this.id = profile.getId();
			this.name = profile.getName();
			this.genderId = profile.getGenderId();
			this.gender = profile.getGender().getGender();
			this.birthday = profile.getBirthday();
			this.height = profile.getHeight();
			this.occupationName = profile.getOccupation().getOccupationName();
			this.income = profile.getIncome();
			this.text = profile.getText();
			this.mail = profile.getUser().getMail();
			//画像ファイルをBASE64形式でエンコード
			this.image = Base64.getEncoder().encodeToString(profile.getImage());
		}
	}
}
