package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Veritaserum {
    //Степень эффекта от зелья правды
    EVERYTHING("EVERYTHING"), //выпивший зелье выдаст всех участников Отряда Дамблдора
    HALF("HALF"), //выпивший зелье выдаст половину участников Отряда Дамблдора
    NOTHING("NOTHING"); //выпивший зелье не сдаст никого
    private final String effect;
}
