package com.itquasar.multiverse.tn3270j;

import com.itquasar.multiverse.tn3270j.status.CommandExecutionTime;
import com.itquasar.multiverse.tn3270j.status.ConnectionStatus;
import com.itquasar.multiverse.tn3270j.status.EmulatorMode;
import com.itquasar.multiverse.tn3270j.status.FieldProtection;
import com.itquasar.multiverse.tn3270j.status.KeyboardState;
import com.itquasar.multiverse.tn3270j.status.ScreenFormatting;
import com.itquasar.multiverse.tn3270j.status.StatusCode;
import com.itquasar.multiverse.tn3270j.status.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;


/**
 * @author Guilherme I F L Weizenmann
 * @see http://x3270.bgp.nu/Unix/x3270-script.html#Status-Format
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
                // FIXME use logger
                throwable.printStackTrace();
            }
        }
        return status;
    }
}
