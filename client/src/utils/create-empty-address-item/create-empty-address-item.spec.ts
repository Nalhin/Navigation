import { createEmptyAddressItem } from './create-empty-address-item';

describe('createEmptyAddressItem', () => {
  it('should return address item with unique id', () => {
    const firstAddress = createEmptyAddressItem({ latitude: 1, longitude: 1 });
    const secondAddress = createEmptyAddressItem({ latitude: 1, longitude: 1 });

    expect(firstAddress.id).not.toBe(secondAddress.id);
  });
});
