package pl.kormateusz.weather.domain.usecases

class ValidateSearchQueryUseCase : BaseParamUseCase<String, Boolean>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun buildUseCase(locationName: String): Boolean =
        Regex(QUERY_REGEX).matches(locationName)

    private companion object {
        private const val QUERY_REGEX = "^\$|^[A-Za-zĄąĆćĘęŁłŃńÓóŚśŹźŻż\\s-]+\$"
    }
}