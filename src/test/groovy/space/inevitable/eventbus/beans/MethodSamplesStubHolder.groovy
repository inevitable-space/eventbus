package space.inevitable.eventbus.beans

import space.inevitable.eventbus.Subscribe
import java.lang.reflect.Method

public class MethodSamplesStubHolder {
    private static class InvalidMethodHolder {
        @SuppressWarnings( [ "GroovyUnusedDeclaration", "GrMethodMayBeStatic" ] )
        //testing purposes
        private int invalidMethod() {
            return 0
        }
    }

    public static Method buildInvalidMethod() {
        InvalidMethodHolder.getDeclaredMethod( "invalidMethod" )
    }

    private static class ValidMethodHolder {
        @Subscribe
        public void validMethod( String event ) {
        }
    }

    public static Method buildValidMethod() {
        ValidMethodHolder.getDeclaredMethod( "validMethod", String )
    }
}
