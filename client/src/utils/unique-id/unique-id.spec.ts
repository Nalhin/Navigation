import { uniqueId } from './unique-id';

describe('uniqueId', () => {
  it('should generate unique id each time it is called', () => {
    const generator = uniqueId();

    expect(generator.getAndIncrement()).not.toBe(generator.getAndIncrement());
  });

  it('should start with the same value', () => {
    const firstGenerator = uniqueId();
    const secondGenerator = uniqueId();

    expect(firstGenerator.getAndIncrement()).toBe(
      secondGenerator.getAndIncrement(),
    );
  });
});
