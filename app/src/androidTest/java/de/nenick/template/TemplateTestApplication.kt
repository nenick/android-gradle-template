package de.nenick.template

class TemplateTestApplication : TemplateApplication() {
    override fun setupDependencyInjection() {
        // We disable the default dependency setup to force each test to register his own mocks and stubs.
        // Note: ViewModels don't need be to configured and they're still available for injection.
    }
}