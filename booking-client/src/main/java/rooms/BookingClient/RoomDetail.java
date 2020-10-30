package tacos.ingredientclient;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
public class RoomDetail {

  private final String id;
  private final String name;
  private final Type type;

  public static enum Type {
    SINGLE, DOUBLE, QUEEN, SUITE, STUDIO
  }

}