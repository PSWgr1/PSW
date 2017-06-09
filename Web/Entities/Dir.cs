using System.Collections.Generic;

namespace Web.Entities
{
    public class Dir : IElement
    {
        public int Id { get; set; } = 0;
        public Dir ParentDir { get; set; }
        public List<IElement> DirContent { get; set; } = new List<IElement>();
        public string Owner { get; set; } = string.Empty;
        public Accessibility Accessibility { get; set; } = Accessibility.Private;
    }
}