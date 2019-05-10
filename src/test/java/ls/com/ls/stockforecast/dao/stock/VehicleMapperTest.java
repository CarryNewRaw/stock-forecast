package sj.com.ls.stockforecast.dao.vehicle;

import org.junit.Test;
import sj.com.ls.stockforecast.AbstractTest;

import java.util.HashMap;

/**
 * @author yaochenglong
 * @date 2018/10/30
 */
public class VehicleMapperTest extends AbstractTest {

//    @Autowired
//    private VehicleMapper vehicleMapper;

    @Test
    public void selectByPrimaryKey(){
//        Vehicle vehicle = vehicleMapper.selectByPrimaryKey(116L);
//        assertNotNull(vehicle);
    }


    @Test
    public void insert(){

    }

    @Test
    public void findList(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("license_card", "沪A");
        map.put("delete_flag", 1);
//        List<Vehicle> vehicleList = vehicleMapper.findList(map);
//        assertNotNull(vehicleList);
    }


}
