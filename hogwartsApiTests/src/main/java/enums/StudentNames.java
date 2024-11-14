package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StudentNames {
    HARRY_POTTER("Гарри Поттер"),
    RONALD_WISLEY("Рон Уизли"),
    GERMIONE_GRANGER("Гермиона Грейнджер"),
    NEVILL_LONGBOTTOM("Невилл Долгопупс");
    private final String name;
}
