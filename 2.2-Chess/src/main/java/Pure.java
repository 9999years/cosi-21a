/**
 * Pure indicates that a method doesn't mutate the state of the object it's
 * invoked on
 *
 * Any methods that call any methods marked Mutate MUST NOT be annotated as Pure
 */
@interface Pure {
}
