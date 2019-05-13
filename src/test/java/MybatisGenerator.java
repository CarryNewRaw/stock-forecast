import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yaochenglong
 * @date 2018/9/5
 * <p>
 * 生成mybatis相关代码
 * 将数据库中已有的表，自动生成表对应的实体类、mapper.xml，mapper类
 */
public class MybatisGenerator {

    public static void generate(String path) throws Exception {
        //MBG 执行过程中的警告信息
        List<String> warnings = new ArrayList<String>();

        //当生成的代码重复时，覆盖原代码
        boolean overwrite = true;

        //读取 MBG 配直文件
        Configuration config = null;
        try {
            InputStream is = MybatisGenerator.class.getResourceAsStream(path);
            ConfigurationParser cp = new ConfigurationParser(warnings);
            config = cp.parseConfiguration(is);
            is.close();
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            //创建 MBG
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            //执行生成代码
            myBatisGenerator.generate(null);
            //输出警告信息
            for (String warning : warnings) {
                System.out.println(warning);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        }
    }

    public static void generateBase() throws Exception {
        generate("/generator/generatorConfig.xml");
    }

    public static void main(String[] args) throws Exception {
//        MybatisGenerator.generate("/generator/generatorConfig.xml");
        MybatisGenerator.generateBase();
    }
}
