package jp.neechan.akari.dictionary.core_impl.data.framework.db.typeconverters

/**
 * Contract for Room type converters.
 *
 * @param R is a restored data type.
 * @param S is a saved data type.
 */
interface AbstractTypeConverter<R, S> {

    fun save(restored: R): S

    fun restore(saved: S): R
}