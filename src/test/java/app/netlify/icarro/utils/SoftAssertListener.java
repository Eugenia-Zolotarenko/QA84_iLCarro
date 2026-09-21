package app.netlify.icarro.utils;

import app.netlify.icarro.core.TestBase;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;


public class SoftAssertListener implements IInvokedMethodListener {
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        // Проверяем, что это был именно тестовый метод, а не конфигурационный (@Before/@After)
        if (method.isTestMethod()) {
            SoftAssert sa = TestBase.getSoftAssert();
            if (sa != null) {
                try {
                    // Вызываем assertAll автоматически!
                    sa.assertAll();
                } catch (AssertionError e) {
                    // Если упало — принудительно ставим тесту статус FAILED
                    testResult.setStatus(ITestResult.FAILURE);
                    // Передаем ошибку со списком всех упавших проверок в отчет
                    testResult.setThrowable(e);
                }
            }
        }
    }
}
