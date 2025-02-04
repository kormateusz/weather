package pl.kormateusz.weather.domain.usecases

abstract class BaseUseCase<E> {
    protected abstract fun buildUseCase(): E

    open fun execute(): E {
        return this.buildUseCase()
    }
}

abstract class BaseParamUseCase<T, E> {
    protected abstract fun buildUseCase(param: T): E

    open fun execute(param: T): E {
        return this.buildUseCase(param)
    }
}

abstract class BaseParamSuspendUseCase<T, E> {
    protected abstract suspend fun buildUseCase(param: T): E

    open suspend fun execute(param: T): E {
        return this.buildUseCase(param)
    }
}