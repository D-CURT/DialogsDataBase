package utils.interceptors;

import org.reflections.Reflections;

import java.util.Set;

public enum EntitiesScope {
    ALL {
        @Override
        void thisType(InterceptorsManager manager, Class type, Object o) {
            Set<Class<?>> subClasses = REFLECTIONS.getSubTypesOf(Object.class);
            subClasses.forEach(aClass -> manager.addingMap(type, o));
        }
    },
    SUB_CLASSES {
        @Override
        void thisType(InterceptorsManager manager, Class type, Object o) {
            Set<Class<?>> subClasses = REFLECTIONS.getSubTypesOf(type);
            subClasses.forEach(aClass -> manager.addingMap(aClass, o));
        }
    },
    THIS {
        @Override
        void thisType(InterceptorsManager manager, Class type, Object o) {
            manager.addingMap(type, o);
        }
    };

    private static final Reflections REFLECTIONS = new Reflections("entities");
    abstract void thisType(InterceptorsManager manager,Class type, Object o);
}
