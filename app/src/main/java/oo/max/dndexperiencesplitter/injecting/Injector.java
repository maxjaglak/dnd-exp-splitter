package oo.max.dndexperiencesplitter.injecting;

import android.content.Context;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class Injector {

    private final Context context;

    private ApplicationModule applicationModule;

    @Getter
    private ApplicationComponent applicationComponent;

    public Injector(Context context) {
        this.context = context.getApplicationContext();
        buildComponents();
    }

    protected void buildComponents() {
        applicationModule = new ApplicationModule(context);

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .build();
    }

    public static void invokeInjection(Object target, Object component) {
        try {
            Method[] methods = component.getClass().getDeclaredMethods();
            for (Method method : methods) {
                List<Class<?>> params = Arrays.asList(method.getParameterTypes());
                if (params.contains(target.getClass())) {
                    method.invoke(component, target);
                    return;
                }
            }
            throw new RuntimeException("Inject mothod not found!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}