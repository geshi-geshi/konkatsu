

package com.example.konkatsu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.konkatsu.service.LoginUserDetailsService;

@EnableWebSecurity                //Spring Securityの基本的な設定（認証フィルタの設定など）が行われる
public class SecurityConfig extends WebSecurityConfigurerAdapter {    //WebSecurityConfigurerAdapter を継承することでデフォルト設定に対して追加したい箇所だけオーバーライドして設定できる

    @Override
    public void configure(WebSecurity web) throws Exception {     //configure(WebSecurity)メソッドをオーバーライドすることで特定のリクエストに対してセキュリティー設定を無視する設定など、全体に関わる設定ができる
        web.ignoring().antMatchers("/webjars/**", "/css/**");     //「/webjars」「/css」といった静的リソースに対するアクセスにはセキュリティの設定は無視するようにする
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {   //configure(HttpSecurity)メソッドをオーバーライドすることで、認可の設定やログイン、ログアウトに関する設定ができる
        http
            .authorizeRequests()							//認証が必要となるURLを設定
                    .antMatchers("/login").permitAll()      	//「/login」URLは認識不要。任意のユーザーがアクセスできるようにする
                    .antMatchers("/RegisterUser").permitAll()
                    .antMatchers("/createUser").permitAll()
                    .antMatchers("/createProfile").permitAll()
                   // .antMatchers("/konkatsu").permitAll()
                    .antMatchers("/RegisterUserForm").permitAll()  //ユーザー登録は認識不要
                    .anyRequest().authenticated()			//それ以外のパスには、認証なしでアクセスできないようにする



                 .and()
            .formLogin().loginProcessingUrl("/login")     							//ログインに関する設定
                    .loginPage("/login")              								//ログインフォーム表示のパス(URL)
                    .failureUrl("/login?error")           						//認証失敗時の遷移先(?errorとつけておくとthymeleafの方でエラーのメッセージを出すときに便利)
                    .defaultSuccessUrl("/konkatsu", true)							/*認証成功時の遷移先(第2引数のboolean
                    																	true : ログイン画面した後必ずtopにとばされる
                    																	false : (認証されてなくて一度ログイン画面に飛ばされても)ログインしたら指定したURLに飛んでくれる) */
                    .usernameParameter("mail").passwordParameter("pass")			//メールアドレス、パスワードのパラメータ名を設定(ログイン画面のhtmlのinputのname属性を見に行っている)
            .and()
            .logout()
                    .logoutSuccessUrl("/logout");

        }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 				 //パスワードをハッシュ化する
    }


    /*このクラスは認証処理時に自動で呼ばれるクラス
    やっていることは入力されたパスワードに対してBCryptでハッシュ化し、入力値が正しいか認証を行っている*/
    @Configuration
    protected static class AuthenticationConfiguration
            extends GlobalAuthenticationConfigurerAdapter {
        @Autowired
        LoginUserDetailsService userDetailsService;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
        }
    }
}
