namespace Web.Entities
{
    public interface IElement
    {
        Accessibility Accessibility { get; }
        string Owner { get; }
    }
}