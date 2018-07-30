package cn.kgc.utils;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.util.DruidPasswordCallback;


public class DBPoolConnection extends DruidPasswordCallback {

	   /**
	 * 
	 */
	private static final long serialVersionUID = 6219350516143890820L;
	private static DBPoolConnection dbPoolConnection = null;
	   private static DruidDataSource druidDataSource = null;
	   
	   static {
	       Properties properties = loadPropertiesFile("db-druid-mysql.properties");
	       
	       
	       
	       try {
	           druidDataSource = (DruidDataSource)DruidDataSourceFactory.createDataSource(properties);    //DruidDataSrouce����ģʽ
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	   }
	   
	   
	   /**
	    * ���ݿ����ӳص���
	    * @return
	    */
	   public static synchronized DBPoolConnection getInstance(){
	       if (null == dbPoolConnection){
	           dbPoolConnection = new DBPoolConnection();
	       }
	       return dbPoolConnection;
	   }

	   /**
	    * ����druid���ݿ�����
	    * @return
	    * @throws SQLException
	    */
	   public DruidPooledConnection getConnection() throws SQLException{
	       return druidDataSource.getConnection();
	   }
	   /**
	    * @param string �����ļ���
	    * @return Properties����
	    */
	   private static Properties loadPropertiesFile(String fullFile) {
	       if (null == fullFile || fullFile.equals("")){
	           throw new IllegalArgumentException("Properties file path can not be null" + fullFile);
	       }
	       InputStream inputStream = DBPoolConnection.class.getClassLoader().getResourceAsStream(fullFile);
	       Properties p =null;
	       try {
	           p = new Properties();
	           p.load(inputStream);
	           String publicKey = p.getProperty("publicKey");
	           String password = p.getProperty("password");
	           String decryptPassword=ConfigTools.decrypt(publicKey, password);
	           p.setProperty("password", decryptPassword);
	       } catch (Exception e) {
	           e.printStackTrace();
	       } finally {
	           try {
	               if (null != inputStream){
	                   inputStream.close();
	               }
	           } catch (Exception e) {
	               e.printStackTrace();
	           }
	       }
	       
	       return p;
	   }
}
