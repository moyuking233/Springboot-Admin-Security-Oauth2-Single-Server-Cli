import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.nga.admin.AdminApplication;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = AdminApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
@Data
public class TestDemo {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void getEncryPassword(){
        System.out.println("test begin");
        String encode = passwordEncoder.encode("123");
        System.out.println(encode);

        Digester md5 = new Digester(DigestAlgorithm.MD5);
        System.out.println(md5.digestHex("123"));

        md5.setSalt("localhost".getBytes());
        String digestHex = md5.digestHex("123");



        System.out.println(digestHex);

    }

}
