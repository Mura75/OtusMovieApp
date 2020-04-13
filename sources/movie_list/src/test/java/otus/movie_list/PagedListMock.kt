package otus.movie_list

import android.database.Cursor
import androidx.paging.*

fun <T> List<T>.asPagedList(config: PagedList.Config? = null): PagedList<T>? {
    val defaultConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(size)
        .setMaxSize(size + 2)
        .setPrefetchDistance(1)
        .build()

    val pagedList = RxPagedListBuilder(
        createMockDataSourceFactory(this),
        1
    ).buildObservable()
        .blockingFirst()
    return pagedList
}


private fun <T> createMockDataSourceFactory(itemList: List<T>): DataSource.Factory<Int, T> =
    object : DataSource.Factory<Int, T>() {
        override fun create(): DataSource<Int, T> = MockLimitDataSource(itemList)
    }

class MockLimitDataSource<T>(private val itemList: List<T>) : PageKeyedDataSource<Int, T>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        callback.onResult(itemList, null, 0)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        callback.onResult(itemList, 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {

    }
}