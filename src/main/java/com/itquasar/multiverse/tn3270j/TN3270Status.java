package com.itquasar.multiverse.tn3270j;

import com.itquasar.multiverse.tn3270j.status.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;


/**
 * @author Guilherme I F L Weizenmann
 * @see <a href="http://x3270.bgp.nu/Unix/x3270-script.html#Status-Format">Status-Format</a>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class TN3270Status implements StatusCode {

    private String status;

    private int modelNumber;
    private int numberOfRows;
    private int numberOfColumns;
    private int cursorRow;
    private int cursorColumn;
    private int windowID;

    private KeyboardState keyboardState;
    private ScreenFormatting screenFormatting;
    private FieldProtection fieldProtection;
    private ConnectionStatus connectionState;
    private EmulatorMode emulatorMode;
    private CommandExecutionTime commandExecutionTime;

    private Status code;

    @Override
    public String code() {
        return this.status;
    }

    @Override
    public String toString() {
        return this.status;
    }

    @SneakyThrows
    public static TN3270Status from(Object object) {
        TN3270Status status = new TN3270Status();
        if (object == null) {
            return status;
        }
        for (Field field : status.getClass().getDeclaredFields()) {
            if (field.getName().equals("LOGGER")) {
                continue;
            }
            try {
                Field declaredField = object.getClass().getDeclaredField(field.getName());
                declaredField.setAccessible(true);
                Object value = declaredField.get(object);
                if (field.getType().isEnum() && com.j3270.base.Status.StatusField.class.isInstance(value)) {
                    value = StatusCode.fromCode((StatusCode[]) field.getType().getDeclaredMethod("values").invoke(null), ((com.j3270.base.Status.StatusField) value).code());
                } else if (StatusCode.class.isAssignableFrom(field.getType())) {
                    value = field.getType().getDeclaredMethod("from", value.getClass()).invoke(null, value);
                }
                field.set(status, value);
            } catch (Throwable throwable) {
                LOGGER.error("Error building status object: " + throwable, throwable);
            }
        }
        return status;
    }
}
