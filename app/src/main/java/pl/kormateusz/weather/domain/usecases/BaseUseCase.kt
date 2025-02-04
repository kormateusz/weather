package pl.kormateusz.weather.domain.usecases

abstract class BaseParamUseCase<T, E> {
    protected abstract fun buildUseCase(param: T): E

    open fun execute(param: T): E {
        return this.buildUseCase(param)
    }
}