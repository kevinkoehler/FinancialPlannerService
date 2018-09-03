package app.kevnet.fps.util;

import app.kevnet.fps.bean.Entry;
import app.kevnet.fps.bean.Plan;
import app.kevnet.fps.enums.Frequency;
import app.kevnet.fps.enums.Type;
import java.math.BigDecimal;
import org.apache.commons.lang3.RandomUtils;

public class TestUtil {

  private static final String NAME = "Test Name";

  public static Plan getPlan() {
    return new Plan(null, NAME,
        new BigDecimal(RandomUtils.nextInt()),
        new BigDecimal(RandomUtils.nextInt()));
  }

  public static Entry getEntry() {
    int typeIndex = RandomUtils.nextInt(0, Type.values().length);
    int frequencyIndex = RandomUtils.nextInt(0, Frequency.values().length);
    return new Entry(null, null,
        NAME,
        new BigDecimal(RandomUtils.nextInt()),
        Type.values()[typeIndex], Frequency.values()[frequencyIndex]);
  }

}
